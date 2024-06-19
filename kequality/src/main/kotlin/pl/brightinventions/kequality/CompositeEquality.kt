package pl.brightinventions.kequality

class CompositeEquality<T>(private val components: Iterable<Equality<T>>) : Equality<T> {

    constructor(vararg components: Equality<T>) : this(components.toList())

    override fun areEqual(o1: T, o2: T): Boolean {
        return components.all { it.areEqual(o1, o2) }
    }
}
