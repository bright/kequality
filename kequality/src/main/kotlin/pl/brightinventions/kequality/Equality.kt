package pl.brightinventions.kequality

/**
 * Custom equality checker
 */
interface Equality<T> {
    /**
     * @return true when [o1] is equal to [o2] and false otherwise
     */
    fun areEqual(o1: T, o2: T): Boolean
}
