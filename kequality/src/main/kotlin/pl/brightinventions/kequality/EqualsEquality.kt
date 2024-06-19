package pl.brightinventions.kequality

class EqualsEquality<T> : Equality<T> {
    override fun areEqual(o1: T, o2: T): Boolean {
        return if (o1 == null && o2 == null) {
            true
        } else {
            o1?.equals(o2) ?: false
        }
    }
}