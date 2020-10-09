package pl.brightinventions.kequality

class ComparableNullableEquality<T : Comparable<T>> : Equality<T?> {
    private val comparableEquality = ComparableEquality<T>()
    private val equalsEquality = EqualsEquality<T?>()

    override fun areEqual(o1: T?, o2: T?): Boolean {
        return if (o1 != null && o2 != null) {
            comparableEquality.areEqual(o1, o2)
        } else {
            equalsEquality.areEqual(o1, o2)
        }
    }
}