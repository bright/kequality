package pl.brightinventions.kequality.diffutil

import androidx.recyclerview.widget.DiffUtil

interface DiffUtilIdentityCheck<T> {
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean
}

interface DiffUtilContentCheck<T> {
    fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

interface DiffUtilChangePayloadCreator<T> {
    fun getChangePayload(oldItem: T, newItem: T): Any?
}

private class NullChangePayloadCreator<T> : DiffUtilChangePayloadCreator<T> {
    override fun getChangePayload(oldItem: T, newItem: T): Any? = null
}

class DiffUtilDelegatingItemCallback<T>(
    val diffUtilIdentityCheck: DiffUtilIdentityCheck<T>,
    val diffUtilContentCheck: DiffUtilContentCheck<T>,
    val diffUtilChangePayloadCreator: DiffUtilChangePayloadCreator<T> = NullChangePayloadCreator()
) : DiffUtil.ItemCallback<T>(),
    DiffUtilIdentityCheck<T> by diffUtilIdentityCheck,
    DiffUtilContentCheck<T> by diffUtilContentCheck,
    DiffUtilChangePayloadCreator<T> by diffUtilChangePayloadCreator {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        diffUtilIdentityCheck.areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        diffUtilContentCheck.areContentsTheSame(oldItem, newItem)

    override fun getChangePayload(oldItem: T, newItem: T): Any? =
        diffUtilChangePayloadCreator.getChangePayload(oldItem, newItem)
}

class DiffUtilDelegatingCallback<T>(
    private val oldItems: Collection<T>,
    private val newItems: Collection<T>,
    private val diffUtilIdentityCheck: DiffUtilIdentityCheck<T>,
    private val diffUtilContentCheck: DiffUtilContentCheck<T>,
    private val diffUtilChangePayloadCreator: DiffUtilChangePayloadCreator<T> = NullChangePayloadCreator()
) : DiffUtil.Callback(),
    DiffUtilIdentityCheck<T> by diffUtilIdentityCheck,
    DiffUtilContentCheck<T> by diffUtilContentCheck,
    DiffUtilChangePayloadCreator<T> by diffUtilChangePayloadCreator {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return diffUtilIdentityCheck.areItemsTheSame(
            oldItems.elementAt(oldItemPosition),
            newItems.elementAt(newItemPosition)
        )
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return diffUtilContentCheck.areContentsTheSame(
            oldItems.elementAt(oldItemPosition),
            newItems.elementAt(newItemPosition)
        )
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return diffUtilChangePayloadCreator.getChangePayload(
            oldItems.elementAt(oldItemPosition),
            newItems.elementAt(newItemPosition)
        )
    }
}

fun <T> DiffUtilDelegatingCallback(
    oldItems: Collection<T>,
    newItems: Collection<T>,
    diffUtilDelegatingItemCallback: DiffUtilDelegatingItemCallback<T>
) = DiffUtilDelegatingCallback(
    oldItems,
    newItems,
    diffUtilDelegatingItemCallback.diffUtilIdentityCheck,
    diffUtilDelegatingItemCallback.diffUtilContentCheck,
    diffUtilDelegatingItemCallback.diffUtilChangePayloadCreator
)
