package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual
import java.math.BigDecimal

class PropertyEqualityTest {

    private class EqualityTestPerson(val name: String, val age: Int, val sex: String?)

    @Test
    fun `should return true for same object`() {
        val person = EqualityTestPerson("Alice", 45, "F")
        EqualityTestPerson::name.equalsEquality.areEqual(person, person).shouldEqual(true)
        EqualityTestPerson::age.equalsEquality.areEqual(person, person).shouldEqual(true)
        EqualityTestPerson::sex.equalsEquality.areEqual(person, person).shouldEqual(true)
    }

    @Test
    fun `should return true for identical objects`() {
        val person1 = EqualityTestPerson("Alice", 45, "F")
        val person2 = EqualityTestPerson("Alice", 45, "F")
        EqualityTestPerson::name.equalsEquality.areEqual(person1, person2).shouldEqual(true)
        EqualityTestPerson::age.equalsEquality.areEqual(person1, person2).shouldEqual(true)
        EqualityTestPerson::sex.equalsEquality.areEqual(person1, person2).shouldEqual(true)
    }

    @Test
    fun `should return false for entirely different object`() {
        val person1 = EqualityTestPerson("Alice", 45, "F")
        val person2 = EqualityTestPerson("Bob", 50, "M")
        EqualityTestPerson::name.equalsEquality.areEqual(person1, person2).shouldEqual(false)
        EqualityTestPerson::age.equalsEquality.areEqual(person1, person2).shouldEqual(false)
        EqualityTestPerson::sex.equalsEquality.areEqual(person1, person2).shouldEqual(false)
    }

    @Test
    fun `should return true for null references`() {
        val person1 = EqualityTestPerson("Alice", 45, null)
        val person2 = EqualityTestPerson("Bob", 50, null)
        EqualityTestPerson::sex.equalsEquality.areEqual(person1, person2).shouldEqual(true)
    }

    @Test
    fun `should return false for null vs non-null references`() {
        val person1 = EqualityTestPerson("Alice", 45, "F")
        val person2 = EqualityTestPerson("Bob", 50, null)
        EqualityTestPerson::sex.equalsEquality.areEqual(person1, person2).shouldEqual(false)
    }

    private class EqualityTestAmount(val value: BigDecimal)

    @Test
    fun `regular equality should return false and comparable equality should return true for same amounts`() {
        val amount1 = EqualityTestAmount(BigDecimal("1").setScale(2))
        val amount2 = EqualityTestAmount(BigDecimal("1").setScale(3))
        EqualityTestAmount::value.equalsEquality.areEqual(amount1, amount2).shouldEqual(false)
        EqualityTestAmount::value.comparableEquality.areEqual(amount1, amount2).shouldEqual(true)
    }

    @Test
    fun `should return false for different amounts`() {
        val amount1 = EqualityTestAmount(BigDecimal("1"))
        val amount2 = EqualityTestAmount(BigDecimal("2"))
        EqualityTestAmount::value.comparableEquality.areEqual(amount1, amount2).shouldEqual(false)
    }

    private class NullableEqualityTestAmount(val value: BigDecimal?)

    @Test
    fun `comparable nullable equality should return true for nulls`() {
        val amount1 = NullableEqualityTestAmount(null)
        val amount2 = NullableEqualityTestAmount(null)
        NullableEqualityTestAmount::value.comparableNullableEquality.areEqual(amount1, amount2)
            .shouldEqual(true)
    }

    @Test
    fun `comparable nullable equality should return false for null and amount`() {
        val amount1 = NullableEqualityTestAmount(null)
        val amount2 = NullableEqualityTestAmount(BigDecimal("1"))
        NullableEqualityTestAmount::value.comparableNullableEquality.areEqual(amount1, amount2)
            .shouldEqual(false)
    }

    @Test
    fun `comparable nullable equality should return false for different amounts`() {
        val amount1 = NullableEqualityTestAmount(BigDecimal("1"))
        val amount2 = NullableEqualityTestAmount(BigDecimal("2"))
        NullableEqualityTestAmount::value.comparableNullableEquality.areEqual(amount1, amount2)
            .shouldEqual(false)
    }

    @Test
    fun `regular equality should return false and comparable nullable equality should return true for same amounts`() {
        val amount1 = NullableEqualityTestAmount(BigDecimal("1").setScale(2))
        val amount2 = NullableEqualityTestAmount(BigDecimal("1").setScale(3))
        NullableEqualityTestAmount::value.equalsEquality.areEqual(amount1, amount2).shouldEqual(false)
        NullableEqualityTestAmount::value.comparableNullableEquality.areEqual(amount1, amount2).shouldEqual(true)
    }
}