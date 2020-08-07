package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual
import java.math.BigDecimal

class ComparableEqualityTest {

    @Test
    fun `RegularEquality should return false but ComparableEquality should return true for BigDecimals`() {
        val amount1 = BigDecimal.ONE
        val amount2 = BigDecimal("1").setScale(2)
        (amount1 == amount2).shouldEqual(false)
        EqualsEquality<BigDecimal>().areEqual(amount1, amount2).shouldEqual(false)
        (amount1.compareTo(amount2) == 0).shouldEqual(true)
        ComparableEquality<BigDecimal>().areEqual(amount1, amount2).shouldEqual(true)
    }
}