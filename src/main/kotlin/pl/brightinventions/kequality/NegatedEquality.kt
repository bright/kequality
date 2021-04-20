package pl.brightinventions.kequality

class NegatedEquality<T>(private val equality: Equality<T>) : Equality<T> {

    override fun areEqual(o1: T, o2: T): Boolean {
        return !equality.areEqual(o1, o2)
    }
}

val <T> Equality<T>.negated get() = NegatedEquality(this)
