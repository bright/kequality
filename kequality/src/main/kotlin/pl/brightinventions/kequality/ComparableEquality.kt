package pl.brightinventions.kequality

class ComparableEquality<T : Comparable<T>> : Equality<T> {
    override fun areEqual(o1: T, o2: T): Boolean {
        return o1.compareTo(o2) == 0
    }
}