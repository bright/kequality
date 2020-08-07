package pl.brightinventions.kequality

import kotlin.reflect.KProperty1

fun <T> List<T>.equalsBy(other: List<T>, equality: Equality<T>, ignoreOrder: Boolean = false): Boolean {
    return ListEquality(equality, ignoreOrder)
        .areEqual(this, other)
}

fun <T> List<T>.equalsBy(other: List<T>, vararg properties: KProperty1<T, Any?>): Boolean {
    val equalities = properties.map { it.equalsEquality }
    val composite = CompositeEquality(equalities)
    return equalsBy(other, composite, false)
}

fun <T> List<T>.equalsByIgnoreOrder(other: List<T>, vararg properties: KProperty1<T, Any?>): Boolean {
    val equalities = properties.map { it.equalsEquality }
    val composite = CompositeEquality(equalities)
    return equalsBy(other, composite, true)
}