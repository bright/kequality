package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual
import java.math.BigDecimal

class ComparableNullableEqualityTest {

    @Test
    fun `RegularEquality should return false but ComparableNullableEquality should return true for BigDecimals`() {
        val amount1: BigDecimal? = BigDecimal.ONE
        val amount2: BigDecimal? = BigDecimal("1").setScale(2)
        (amount1 == amount2).shouldEqual(false)
        (amount1?.compareTo(amount2) == 0).shouldEqual(true)
        ComparableNullableEquality<BigDecimal>().areEqual(amount1, amount2).shouldEqual(true)
    }
}