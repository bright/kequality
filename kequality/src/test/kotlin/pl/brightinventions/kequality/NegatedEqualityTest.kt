package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class NegatedEqualityTest {

    private data class Person(val age: Int)

    private val personEqualsEquality = EqualsEquality<Person>()
    private val negatedPersonEqualsEquality = personEqualsEquality.negated

    private val person1 = Person(20)
    private val person2 = Person(20)
    private val person3 = Person(30)

    @Test
    fun `equal objects should be equal with equals equality`() {
        personEqualsEquality.areEqual(person1, person2).shouldEqual(true)
    }

    @Test
    fun `not equal objects should not be equal with equals equality`() {
        personEqualsEquality.areEqual(person1, person3).shouldEqual(false)
    }

    @Test
    fun `equal objects should not be equal with negated equals equality`() {
        negatedPersonEqualsEquality.areEqual(person1, person2).shouldEqual(false)
    }

    @Test
    fun `not equal objects should be equal with negated equals equality`() {
        negatedPersonEqualsEquality.areEqual(person1, person3).shouldEqual(true)
    }
}
