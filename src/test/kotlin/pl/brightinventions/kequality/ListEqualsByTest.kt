package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class ListEqualsByTest {

    private fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }

    private data class Item(val name: String, val age: Int)

    private val baseList = listOf(
        Item("A", 1),
        Item("B", 2),
        Item("C", 3)
    )

    private val baseListReversed =
        baseList.reversed()

    private val baseListWithMiddleItemSameAsFirst =
        baseList.replace(baseList[1], baseList[0])

    private val baseListWithLastItemRemoved =
        baseList.dropLast(1)

    private val baseListWithAllNamesChanged =
        baseList.map { it.copy(name = "X") }

    @Test
    fun `list should equal itself - all properties`() {
        baseList.equalsBy(baseList, Item::age, Item::name)
            .shouldEqual(true)
    }

    @Test
    fun `list should equal itself - single property`() {
        baseList.equalsBy(baseList, Item::age)
            .shouldEqual(true)
    }

    @Test
    fun `empty list should equal another empty list`() {
        emptyList<Item>().equalsBy(emptyList(), Item::age, Item::name)
            .shouldEqual(true)
    }

    @Test
    fun `list should not equal same reversed list`() {
        baseList.equalsBy(baseListReversed, Item::age, Item::name)
            .shouldEqual(false)
        baseListReversed.equalsBy(baseList, Item::age, Item::name)
            .shouldEqual(false)
    }

    @Test
    fun `list should equal same reversed list`() {
        baseList.equalsByIgnoreOrder(baseListReversed, Item::age, Item::name)
            .shouldEqual(true)
        baseListReversed.equalsByIgnoreOrder(baseList, Item::age, Item::name)
            .shouldEqual(true)
    }

    @Test
    fun `list should not equal same list with one item replaced`() {
        baseList.equalsBy(baseListWithMiddleItemSameAsFirst, Item::age, Item::name)
            .shouldEqual(false)
        baseListWithMiddleItemSameAsFirst.equalsBy(baseList, Item::age, Item::name)
            .shouldEqual(false)
    }

    @Test
    fun `list should not equal same list with last item removed`() {
        baseList.equalsBy(baseListWithLastItemRemoved, Item::age, Item::name)
            .shouldEqual(false)
        baseListWithLastItemRemoved.equalsBy(baseList, Item::age, Item::name)
            .shouldEqual(false)
    }

    @Test
    fun `list should not equal same list with all names changed`() {
        baseList.equalsBy(baseListWithAllNamesChanged, Item::age, Item::name)
            .shouldEqual(false)
        baseListWithAllNamesChanged.equalsBy(baseList, Item::age, Item::name)
            .shouldEqual(false)
    }

    @Test
    fun `list should equal same list with all names changed`() {
        baseList.equalsBy(baseListWithAllNamesChanged, Item::age)
            .shouldEqual(true)
        baseListWithAllNamesChanged.equalsBy(baseList, Item::age)
            .shouldEqual(true)
    }

}
