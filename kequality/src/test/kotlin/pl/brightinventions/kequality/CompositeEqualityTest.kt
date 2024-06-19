package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual
import java.math.BigDecimal

class CompositeEqualityTest {

    private data class Payment(
        val id: Long,
        val amount: BigDecimal
    )

    private val paymentIdEquality = DelegatingEquality<Payment, Long> { id }
    private val paymentAmountEquality = DelegatingEquality<Payment, BigDecimal>(ComparableEquality()) { amount }

    private val paymentEquality: Equality<Payment> = CompositeEquality(paymentIdEquality, paymentAmountEquality)

    private val payment = Payment(id = 1L, amount = BigDecimal("10"))

    @Test
    fun `payment should be equal to itself`() {
        paymentEquality.areEqual(payment, payment).shouldEqual(true)
    }

    @Test
    fun `payment should be equal to its copy`() {
        paymentEquality.areEqual(payment, payment.copy()).shouldEqual(true)
    }

    @Test
    fun `payment should not be equal to another payment with a different ID`() {
        val anotherPayment = payment.copy(id = 2L)
        paymentEquality.areEqual(payment, anotherPayment).shouldEqual(false)
    }

    @Test
    fun `payment should not be equal to another payment with a different amount`() {
        val anotherPayment = payment.copy(amount = BigDecimal("1"))
        paymentEquality.areEqual(payment, anotherPayment).shouldEqual(false)
    }

    @Test
    fun `payment should be equal to another payment with the same amount but different scale`() {
        val anotherPayment = payment.copy(amount = BigDecimal("10").setScale(2))
        (payment == anotherPayment).shouldEqual(false)
        paymentEquality.areEqual(payment, anotherPayment).shouldEqual(true)
    }
}