package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class EqualsEqualityTest {

    private data class Item(val name: String, val age: Int)

    @Test
    fun `data class identical items should equal`() {
        val item1 = Item("a", 1)
        val item2 = Item("a", 1)
        EqualsEquality<Item>().areEqual(item1, item2).shouldEqual(true)
    }

    @Test
    fun `data class copies should equal`() {
        val item1 = Item("a", 1)
        val item2 = item1.copy()
        EqualsEquality<Item>().areEqual(item1, item2).shouldEqual(true)
    }

    @Test
    fun `data class items with changed property should not equal`() {
        val item1 = Item("a", 1)
        val item2 = item1.copy(name = "b")
        EqualsEquality<Item>().areEqual(item1, item2).shouldEqual(false)
    }

    @Test
    fun `data class item and null should not equal`() {
        val item1: Item? = Item("a", 1)
        val item2: Item? = null
        EqualsEquality<Item?>().areEqual(item1, item2).shouldEqual(false)
    }

    @Test
    fun `nulls should equal`() {
        val item1: Item? = null
        val item2: Item? = null
        EqualsEquality<Item?>().areEqual(item1, item2).shouldEqual(true)
    }

    private class NonDataItem(val name: String, val age: Int)

    @Test
    fun `non data class identical items should not equal`() {
        val item1 = NonDataItem("a", 1)
        val item2 = NonDataItem("a", 1)
        EqualsEquality<NonDataItem>().areEqual(item1, item2).shouldEqual(false)
    }

    @Test
    fun `non data class item should equal itself`() {
        val item1 = NonDataItem("a", 1)
        EqualsEquality<NonDataItem>().areEqual(item1, item1).shouldEqual(true)
    }

    private class CustomEqualsItem(val name: String, val age: Int) {
        override fun equals(other: Any?): Boolean {
            return if (other is CustomEqualsItem) {
                other.name == name && other.age == age
            } else {
                false
            }
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + age
            return result
        }
    }

    @Test
    fun `custom equals class identical items should equal`() {
        val item1 =
            CustomEqualsItem("a", 1)
        val item2 =
            CustomEqualsItem("a", 1)
        EqualsEquality<CustomEqualsItem>()
            .areEqual(item1, item2).shouldEqual(true)
    }

    @Test
    fun `custom equals class items with changed property should not equal`() {
        val item1 =
            CustomEqualsItem("a", 1)
        val item2 =
            CustomEqualsItem("b", 1)
        EqualsEquality<CustomEqualsItem>()
            .areEqual(item1, item2).shouldEqual(false)
    }

    @Test
    fun `custom equals class item should not equal null`() {
        val item1 =
            CustomEqualsItem("a", 1)
        val item2: CustomEqualsItem? = null
        EqualsEquality<CustomEqualsItem?>()
            .areEqual(item1, item2).shouldEqual(false)
        EqualsEquality<CustomEqualsItem?>()
            .areEqual(item2, item1).shouldEqual(false)
    }
}