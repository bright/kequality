[![](https://jitpack.io/v/bright/kequality.svg)](https://jitpack.io/#bright/kequality)

# kequality #

Custom equality checking utility.

## Installation ##

First, add JitPack to your repositories block in Gradle build script.

```
repositories {
    maven("https://jitpack.io")
}
```

Then, declare the dependency like:
```
implementation("com.github.bright.kequality:kequality:1.1")
```

## Usage examples ##

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
