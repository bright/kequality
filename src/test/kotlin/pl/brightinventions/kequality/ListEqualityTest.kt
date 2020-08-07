package pl.brightinventions.kequality

import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class ListEqualityTest {

    private data class Item(val name: String)

    private val regularItemEquality =
        EqualsEquality<Item>()

    @Test
    fun `empty list should equal self`() {
        ListEquality(regularItemEquality)
            .areEqual(emptyList(), emptyList())
            .shouldEqual(true)
    }

    @Test
    fun `non empty list should equal self`() {
        val list = listOf(
            Item("a"),
            Item("b")
        )
        ListEquality(regularItemEquality)
            .areEqual(list, list)
            .shouldEqual(true)
    }

    @Test
    fun `non empty list should not equal self reversed`() {
        val list = listOf(
            Item("a"),
            Item("b")
        )
        ListEquality(regularItemEquality)
            .areEqual(list, list.reversed())
            .shouldEqual(false)
    }

    @Test
    fun `non empty list should equal self reversed`() {
        val list = listOf(
            Item("a"),
            Item("b")
        )
        ListEquality(regularItemEquality, true)
            .areEqual(list, list.reversed())
            .shouldEqual(true)
    }

    @Test
    fun `different size lists should not equal`() {
        val list1 = listOf(
            Item("a"),
            Item("b")
        )
        val list2 = listOf(
            Item("a"),
            Item("b"),
            Item("a"),
            Item("b")
        )
        ListEquality(regularItemEquality, true)
            .areEqual(list1, list2)
            .shouldEqual(false)
        ListEquality(regularItemEquality, true)
            .areEqual(list2, list1)
            .shouldEqual(false)
    }
}