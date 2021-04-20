package pl.brightinventions.kequality

import org.junit.Before
import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class NullableEqualityTest {

    private data class Person(val age: Int)

    private val personEqualsEquality = EqualsEquality<Person>()
    private val nullablePersonEqualsEquality = personEqualsEquality.nullable

    private val person1 = Person(20)
    private val person2 = Person(30)
    private var person3: Person? = Person(20)
    private var person4: Person? = null
    private var person5: Person? = null

    @Before
    fun setupNullables() {
        person3 = Person(20)
    }

    @Test
    fun `equal objects with different nullability should be equal`() {
        nullablePersonEqualsEquality.areEqual(person1, person3).shouldEqual(true)
    }

    @Test
    fun `not equal objects with different nullability should not be equal`() {
        nullablePersonEqualsEquality.areEqual(person2, person3).shouldEqual(false)
    }

    @Test
    fun `object and null should not be equal`() {
        nullablePersonEqualsEquality.areEqual(person1, person4).shouldEqual(false)
    }

    @Test
    fun `nullable object and null should not be equal`() {
        nullablePersonEqualsEquality.areEqual(person3, person4).shouldEqual(false)
    }

    @Test
    fun `not nullable object and null should not be equal`() {
        nullablePersonEqualsEquality.areEqual(person1, person4).shouldEqual(false)
    }

    @Test
    fun `two nulls should be equal`() {
        nullablePersonEqualsEquality.areEqual(person4, person5).shouldEqual(true)
    }
}