package pl.brightinventions.kequality.android.benchmark

import pl.brightinventions.kequality.*
import java.math.BigDecimal

data class Customer(
    val id: Long,
    val name: String,
    val subscriptionType: SubscriptionType,
    val ageInYears: BigDecimal,
    val lastOrderAmount: BigDecimal?,
    val lastOrderChannel: OrderChannel?
)

enum class SubscriptionType {
    Regular, Premium
}

sealed class OrderChannel {
    object Website : OrderChannel()
    data class Partner(val partnerId: Int) : OrderChannel()
}

val CustomerPropertyEquality: Equality<Customer> = CompositeEquality(
    Customer::id.equalsEquality,
    Customer::name.equalsEquality,
    Customer::subscriptionType.equalsEquality,
    Customer::ageInYears.comparableEquality,
    Customer::lastOrderAmount.comparableNullableEquality,
    Customer::lastOrderChannel.equalsEquality
)

val CustomerManualEquality: Equality<Customer> = object : Equality<Customer> {

    override fun areEqual(o1: Customer, o2: Customer): Boolean =
        o1.id == o2.id && o1.name == o2.name && o1.subscriptionType == o2.subscriptionType &&
                o1.ageInYears.compareTo(o2.ageInYears) == 0 &&
                if (o1.lastOrderAmount != null && o2.lastOrderAmount != null) {
                    o1.lastOrderAmount.compareTo(o2.lastOrderAmount) == 0
                } else {
                    o1.lastOrderAmount == null && o2.lastOrderAmount == null
                } && o1.lastOrderChannel == o2.lastOrderChannel
}

val CustomerDelegatingEquality: Equality<Customer> = object : Equality<Customer> {

    private val LongEqualsEquality = EqualsEquality<Long>()
    private val StringEqualsEquality = EqualsEquality<String>()
    private val BigDecimalComparableEquality = ComparableEquality<BigDecimal>()
    private val BigDecimalNullableComparableEquality = ComparableNullableEquality<BigDecimal>()
    private val SubscriptionTypeEqualsEquality = EqualsEquality<SubscriptionType>()
    private val OrderChannelEqualsEquality = EqualsEquality<OrderChannel?>()

    override fun areEqual(o1: Customer, o2: Customer): Boolean =
        LongEqualsEquality.areEqual(o1.id, o2.id) &&
                StringEqualsEquality.areEqual(o1.name, o2.name) &&
                SubscriptionTypeEqualsEquality.areEqual(o1.subscriptionType, o2.subscriptionType) &&
                BigDecimalComparableEquality.areEqual(o1.ageInYears, o2.ageInYears) &&
                BigDecimalNullableComparableEquality.areEqual(
                    o1.lastOrderAmount,
                    o2.lastOrderAmount
                ) && OrderChannelEqualsEquality.areEqual(o1.lastOrderChannel, o2.lastOrderChannel)


}

val Customer1 = Customer(
    id = 1L,
    name = "John",
    subscriptionType = SubscriptionType.Regular,
    ageInYears = BigDecimal("20"),
    lastOrderAmount = null,
    lastOrderChannel = null
)

val Customer2 = Customer(
    id = 2L,
    name = "Alice",
    subscriptionType = SubscriptionType.Regular,
    ageInYears = BigDecimal("21"),
    lastOrderAmount = BigDecimal("100.99"),
    lastOrderChannel = OrderChannel.Website
)

val Customer3 = Customer(
    id = 3L,
    name = "Bob",
    subscriptionType = SubscriptionType.Premium,
    ageInYears = BigDecimal("22"),
    lastOrderAmount = BigDecimal("256.01"),
    lastOrderChannel = OrderChannel.Partner(partnerId = 128)
)

val Customer3Times100 = (1..100).map { Customer3 }

