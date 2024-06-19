package pl.brightinventions.kequality

import kotlin.reflect.KProperty1

class PropertyEquality<T, R : Any?>(
    private val property: KProperty1<T, R>,
    private val delegate: Equality<R>
) : Equality<T> {
    override fun areEqual(o1: T, o2: T): Boolean {
        return delegate.areEqual(property.get(o1), property.get(o2))
    }
}

fun <T, R : Any?> KProperty1<T, R>.equalityBy(by: Equality<R>) =
    PropertyEquality(this, by)

val <T> KProperty1<T, Any?>.equalsEquality
    get() = equalityBy(EqualsEquality())

val <T, R : Comparable<R>> KProperty1<T, R>.comparableEquality
    get() = equalityBy(ComparableEquality())

val <T, R : Comparable<R?>> KProperty1<T, R?>.comparableNullableEquality
    get() = equalityBy(ComparableNullableEquality())