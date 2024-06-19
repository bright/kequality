package pl.brightinventions.kequality

interface EqualityBuilder<T> {
    fun <R> by(delegateEquality: Equality<R> = EqualsEquality(), delegate: T.() -> R)
}

fun <T> Equality(initializer: EqualityBuilder<T>.() -> Unit): Equality<T> =
    EqualityBuilderImpl<T>().apply(initializer).build()

internal class EqualityBuilderImpl<T> : EqualityBuilder<T> {
    private val components = mutableListOf<Equality<T>>()

    override fun <R> by(delegateEquality: Equality<R>, delegate: T.() -> R) {
        components += DelegatingEquality(delegateEquality, delegate)
    }

    fun build(): Equality<T> = CompositeEquality(components)
}
