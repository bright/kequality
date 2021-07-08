package pl.brightinventions.kequality.diffutil

import org.junit.Test
import pl.brightinventions.kequality.EqualsEquality
import pl.miensol.shouldko.shouldEqual
import java.math.BigDecimal

class KequalityDiffUtilTest {

    private val personEqualsItemCallback = DiffItemCallbackById<Person>()
    private val personCustomItemCallback = DiffItemCallbackById(PersonEquality.diffUtilContentCheck())

    private val alice = Person(id = 1L, name = "Alice", age = BigDecimal("20"))
    private val bob = Person(id = 2L, name = "Bob", age = BigDecimal("10"))

    @Test
    fun `both equals and custom equality item callbacks should indicate alice is alice and bob is bob`() {
        personEqualsItemCallback.areItemsTheSame(alice, alice).shouldEqual(true)
        personCustomItemCallback.areItemsTheSame(alice, alice).shouldEqual(true)

        personEqualsItemCallback.areContentsTheSame(alice, alice).shouldEqual(true)
        personCustomItemCallback.areContentsTheSame(alice, alice).shouldEqual(true)

        personEqualsItemCallback.areItemsTheSame(bob, bob).shouldEqual(true)
        personCustomItemCallback.areItemsTheSame(bob, bob).shouldEqual(true)

        personEqualsItemCallback.areContentsTheSame(bob, bob).shouldEqual(true)
        personCustomItemCallback.areContentsTheSame(bob, bob).shouldEqual(true)
    }

    @Test
    fun `both equals and custom equality item callbacks should indicate bob is not alice`() {
        personEqualsItemCallback.areItemsTheSame(alice, bob).shouldEqual(false)
        personEqualsItemCallback.areContentsTheSame(alice, bob).shouldEqual(false)
        personEqualsItemCallback.areItemsTheSame(bob, alice).shouldEqual(false)
        personEqualsItemCallback.areContentsTheSame(bob, alice).shouldEqual(false)

        personCustomItemCallback.areItemsTheSame(alice, bob).shouldEqual(false)
        personCustomItemCallback.areContentsTheSame(alice, bob).shouldEqual(false)
        personCustomItemCallback.areItemsTheSame(bob, alice).shouldEqual(false)
        personCustomItemCallback.areContentsTheSame(bob, alice).shouldEqual(false)
    }

    @Test
    fun `only custom equality item callback should indicate bob with differently defined age property is still bob`() {
        val bob2 = bob.copy(age = BigDecimal("10.0"))

        personEqualsItemCallback.areItemsTheSame(bob, bob2).shouldEqual(true)
        personCustomItemCallback.areItemsTheSame(bob, bob2).shouldEqual(true)

        personEqualsItemCallback.areContentsTheSame(bob, bob2).shouldEqual(false)
        personCustomItemCallback.areContentsTheSame(bob, bob2).shouldEqual(true)
    }

    @Test
    fun `both equals and custom equality callback should indicate the list of alice and bob is the same`() {
        val aliceAndBob = listOf(alice, bob)

        val personEqualsDiffUtilCallback = DiffCallbackById(aliceAndBob, aliceAndBob)
        val personCustomDiffUtilCallback =
            DiffCallbackById(aliceAndBob, aliceAndBob, PersonEquality.diffUtilContentCheck())

        listOf(personEqualsDiffUtilCallback, personCustomDiffUtilCallback).forEach {
            with(it) {
                areItemsTheSame(0, 0).shouldEqual(true)
                areContentsTheSame(0, 0).shouldEqual(true)

                areItemsTheSame(0, 1).shouldEqual(false)
                areContentsTheSame(0, 1).shouldEqual(false)

                areItemsTheSame(1, 1).shouldEqual(true)
                areContentsTheSame(1, 1).shouldEqual(true)

                areItemsTheSame(1, 0).shouldEqual(false)
                areContentsTheSame(1, 0).shouldEqual(false)
            }
        }

    }

    @Test
    fun `both equals and custom equality callback should indicate the reordered list is not the same`() {
        val aliceAndBob = listOf(alice, bob)
        val bobAndAlice = listOf(bob, alice)

        val personEqualsDiffUtilCallback = DiffCallbackById(aliceAndBob, bobAndAlice)
        val personCustomDiffUtilCallback =
            DiffCallbackById(aliceAndBob, bobAndAlice, PersonEquality.diffUtilContentCheck())

        listOf(personEqualsDiffUtilCallback, personCustomDiffUtilCallback).forEach {
            with(it) {
                areItemsTheSame(0, 0).shouldEqual(false)
                areContentsTheSame(0, 0).shouldEqual(false)

                areItemsTheSame(0, 1).shouldEqual(true)
                areContentsTheSame(0, 1).shouldEqual(true)

                areItemsTheSame(1, 1).shouldEqual(false)
                areContentsTheSame(1, 1).shouldEqual(false)

                areItemsTheSame(1, 0).shouldEqual(true)
                areContentsTheSame(1, 0).shouldEqual(true)
            }
        }
    }

    @Test
    fun `only custom equality callback should indicate bob with differently defined age property is still bob`() {
        val bob2 = bob.copy(age = BigDecimal("10.0"))

        val aliceAndBob = listOf(alice, bob)
        val aliceAndBob2 = listOf(alice, bob2)

        val personEqualsDiffUtilCallback = DiffCallbackById(aliceAndBob, aliceAndBob2)
        val personCustomDiffUtilCallback =
            DiffCallbackById(aliceAndBob, aliceAndBob2, PersonEquality.diffUtilContentCheck())

        with(personEqualsDiffUtilCallback) {
            areItemsTheSame(0, 0).shouldEqual(true)
            areContentsTheSame(0, 0).shouldEqual(true)

            areItemsTheSame(0, 1).shouldEqual(false)
            areContentsTheSame(0, 1).shouldEqual(false)

            areItemsTheSame(1, 1).shouldEqual(true)
            areContentsTheSame(1, 1).shouldEqual(false)

            areItemsTheSame(1, 0).shouldEqual(false)
            areContentsTheSame(1, 0).shouldEqual(false)
        }

        with(personCustomDiffUtilCallback) {
            areItemsTheSame(0, 0).shouldEqual(true)
            areContentsTheSame(0, 0).shouldEqual(true)

            areItemsTheSame(0, 1).shouldEqual(false)
            areContentsTheSame(0, 1).shouldEqual(false)

            areItemsTheSame(1, 1).shouldEqual(true)
            areContentsTheSame(1, 1).shouldEqual(true)

            areItemsTheSame(1, 0).shouldEqual(false)
            areContentsTheSame(1, 0).shouldEqual(false)
        }
    }

    @Test
    fun `only custom identity check should indicate bob with differently defined age property is still bob`() {
        val bob2 = bob.copy(age = BigDecimal("10.0"))

        val equalsIdentityCheck = EqualsEquality<Person>().diffUtilIdentityCheck()
        val customIdentityCheck = PersonEquality.diffUtilIdentityCheck()

        equalsIdentityCheck.areItemsTheSame(bob, bob2).shouldEqual(false)
        customIdentityCheck.areItemsTheSame(bob, bob2).shouldEqual(true)
    }
}