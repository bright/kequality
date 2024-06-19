package pl.brightinventions.kequality

class DelegatingEquality<T, R>(
    private val delegateEquality: Equality<R> = EqualsEquality(),
    private val delegate: T.() -> R
) : Equality<T> {

    override fun areEqual(o1: T, o2: T): Boolean {
        return delegateEquality.areEqual(o1.delegate(), o2.delegate())
    }
}

