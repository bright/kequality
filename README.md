[![](https://jitpack.io/v/bright/kequality.svg)](https://jitpack.io/#bright/kequality)

# kequality #

Custom equality checking utility.

It allows you to:

- Extract the custom equality checks to make them reusable
- Use different implementations of the equality check depending on the
  business context
- Write declarative and easily readable equality checking logic
- Simplify Android's RecyclerView DiffUtil usage

See [Usage examples](#usage-examples) below.

## Installation ##

First, add JitPack to your repositories block in Gradle build script.

```gradle
repositories {
    maven("https://jitpack.io")
}
```

Then, declare the dependency like:
```gradle
// Base kequality features
implementation("com.github.bright.kequality:kequality:1.4.0")

// Android's RecyclerView DiffUtil integration
implementation("com.github.bright.kequality:diffutil:1.4.0")
```

## Usage examples ##

### Custom equality check logic ###

```kotlin
data class Address(
    val city: String
)

val AddressIgnoreCaseEquality = object : Equality<Address> {
    override fun areEqual(o1: Address, o2: Address): Boolean {
        return o1.city.equals(o2.city, ignoreCase = true)
    }
}
```

### Complex declarative equality check ###

```kotlin
data class Person(
    val name: String,
    val age: BigDecimal,
    val address: Address
)

val PersonEquality = CompositeEquality(
    Person::name.equalsEquality,    // uses Any.equals()
    Person::age.comparableEquality, // uses Comparable.compareTo()
    Person::address.equalityBy(AddressIgnoreCaseEquality) // see above
)
```

or

```kotlin
val PersonEquality = Equality<Person> {
    by { name }                         // uses Any.equals()
    by(ComparableEquality()) { age }    // uses Comparable.compareTo()
    by(AddressIgnoreCaseEquality) { address } // see above
}
```

### Check if two lists contain equal objects ignoring their order ###

Regular `equals` comparison of two lists containing equal objects ordered differently returns `false`.
Using `ListEquality` you can specify if the order has to be the same to consider the lists equal.

```kotlin
data class Person(val name: String)

val people1 = listOf(Person("Alice"), Person("Bob"))
val people2 = listOf(Person("Bob"), Person("Alice"))

people1 == people2 // returns false

val regularPersonEquality = EqualsEquality<Person>()
val peopleListEquality = ListEquality(regularPersonEquality, ignoreOrder = true)
peopleListEquality.areEqual(people1, people2) // returns true
```

### Check if objects are almost the same (except a single property) ###

You can easily find out if objects are **almost** the same by excluding a single property from the equality check.
All the other properties can be compared using the regular `equals` method.

```kotlin
data class Person(val name: String, val age: Int, val height: Int)

val person1 = Person("Alice", 20, 175)
val person2 = Person("Bob", 20, 175)

val personEquality = CompositeEquality(
    Person::class.declaredMemberProperties
        .filterNot { it == Person::name }
        .map { it.equalsEquality }
)


personEquality.areEqual(person1, person2) // returns true
```

### Simplify Android RecyclerView DiffUtil usage ###

Normally, when you use `ListAdapter`, you prepare a class implementing
`DiffUtil.ItemCallback<T>`, for example:

```kotlin
data class Person(
    val id: Long,
    val name: String,
    val age: BigDecimal
)
```

```kotlin
class PersonDiffUtilItemCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name && oldItem.age.compareTo(newItem.age) == 0
    }
}
```

The logic inside `areItemsTheSame` and `areContentsTheSame` is hardly
reusable and readable, especially when your class has a lot of
properties to compare.

Moreover, if you want to use `DiffUtil.calculateDiff` to manually
calculate the diff between two collections, you must write yet
another class extending `DiffUtil.Callback` like this:

```kotlin
class PersonDiffUtilCallback(val oldItems: List<Person>, val newItems: List<Person>) : DiffUtil.Callback() {
    
    private val itemCallback = PersonDiffUtilItemCallback()
    
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemCallback.areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }
}
```

and use it this way:

```kotlin
DiffUtil.calculateDiff(PersonDiffUtilCallback(items1, items2))
```

With kequality, you can easily convert any `Equality<T>` into
`DiffUtil.ItemCallback<T>` or `DiffUtil.Callback`

#### Convert `Equality<T>` into `DiffUtil.ItemCallback<T>` or `DiffUtil.Callback` ####

```kotlin
data class Person(
    val id: Long,
    val name: String,
    val age: BigDecimal
)

val PersonIdEquality = Equality<Person> {
    by { id }
}

val PersonContentEquality = Equality<Person> {
    by { name }
    by(ComparableEquality()) { age }
}

val PersonDiffUtilItemCallback: DiffUtil.ItemCallback<Person> =
    DiffUtilDelegatingItemCallback(
        diffUtilIdentityCheck = PersonIdEquality.diffUtilIdentityCheck(),
        diffUtilContentCheck = PersonContentEquality.diffUtilContentCheck()
    )

fun PersonDiffUtilCallback(oldItems: List<Person>, newItems: List<Person>): DiffUtil.Callback =
    DiffUtilDelegatingCallback(
        oldItems = oldItems,
        newItems = newItems,
        diffUtilDelegatingItemCallback = PersonDiffUtilItemCallback // or pass another diffUtilIdentityCheck and diffUtilContentCheck
    )
```

#### Use `HasDiffCallbackId` to make it even shorter ####

If you let your class implement `HasDiffCallbackId` interface like this:

```kotlin
data class Person(
    val id: Long,
    val name: String,
    val age: BigDecimal
) : HasDiffCallbackId {
    override val diffCallbackId: Any
        get() = id
}
```

then the previous example can be further shortened to:

```kotlin
val PersonContentEquality = Equality<Person> {
    by { name }
    by(ComparableEquality()) { age }
}

val PersonDiffUtilItemCallback: DiffUtil.ItemCallback<Person> =
    DiffItemCallbackById(
        diffUtilContentCheck = PersonContentEquality.diffUtilContentCheck()
    )

fun PersonDiffUtilCallback(oldItems: List<Person>, newItems: List<Person>): DiffUtil.Callback =
    DiffCallbackById(
        oldItems = oldItems,
        newItems = newItems,
        diffUtilContentCheck = PersonContentEquality.diffUtilContentCheck()
    )
```

#### Even shorter usage for simpler classes ####

The examples above were based on the `Person` class that required
customized equality check because `age` was `BigDecimal` so calling
`equals()` was not good enough (because e.g. `BigDecimal("10.0")` is not
equal to `BigDecimal("10")`)

If your class doesn't need custom equality check and you can rely on
`equals()`, e.g.

```kotlin
data class Person(
    val id: Long,
    val name: String
) : HasDiffCallbackId {
    override val diffCallbackId: Any
        get() = id
}
```

then creating the `DiffUtil` implementations based on
`EqualsEquality<T>` is even simpler:

```kotlin
val PersonDiffUtilItemCallback: DiffUtil.ItemCallback<Person> = DiffItemCallbackById()

fun PersonDiffUtilCallback(oldItems: List<Person>, newItems: List<Person>): DiffUtil.Callback =
    DiffCallbackById(
        oldItems = oldItems,
        newItems = newItems
    )
```
