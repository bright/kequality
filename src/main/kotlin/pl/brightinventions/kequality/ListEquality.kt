package pl.brightinventions.kequality

class ListEquality<T>(
    private val itemEquality: Equality<T>,
    private val ignoreOrder: Boolean = false
) : Equality<List<T>> {

    override fun areEqual(o1: List<T>, o2: List<T>): Boolean {
        if (o1.size != o2.size) {
            return false
        }

        if (ignoreOrder) {
            o1.forEach { item ->
                o2.find { otherItem ->
                    itemEquality.areEqual(item, otherItem)
                } ?: return false
            }
        } else {
            o1.forEachIndexed { index, item ->
                val itemToCompare = o2[index]
                if (!itemEquality.areEqual(item, itemToCompare)) {
                    return false
                }
            }
        }

        return true
    }
}