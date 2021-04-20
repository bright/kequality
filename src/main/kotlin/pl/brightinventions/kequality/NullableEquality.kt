package pl.brightinventions.kequality

class NullableEquality<T : Any>(private val notNullEquality: Equality<T>) : Equality<T?> {

    override fun areEqual(o1: T?, o2: T?): Boolean {
        return if (o1 != null && o2 != null) {
            notNullEquality.areEqual(o1, o2)
        } else {
            o1 == null && o2 == null
        }
    }
}

val <T : Any> Equality<T>.nullable get() = NullableEquality(this)