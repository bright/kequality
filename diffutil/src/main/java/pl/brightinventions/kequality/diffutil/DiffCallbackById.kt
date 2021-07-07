package pl.brightinventions.kequality.diffutil

import androidx.recyclerview.widget.DiffUtil
import pl.brightinventions.kequality.EqualsEquality

interface HasDiffCallbackId {
    val diffCallbackId: Any
}

class DiffUtilIdentityCheckById<T : HasDiffCallbackId> : DiffUtilIdentityCheck<T> {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.diffCallbackId == newItem.diffCallbackId
    }
}

fun <T : HasDiffCallbackId> DiffItemCallbackById(
    diffUtilContentCheck: DiffUtilContentCheck<T> = EqualsEquality<T>().diffUtilContentCheck()
): DiffUtil.ItemCallback<T> =
    DiffUtilDelegatingItemCallback(
        diffUtilIdentityCheck = DiffUtilIdentityCheckById(),
        diffUtilContentCheck = diffUtilContentCheck
    )

fun <T : HasDiffCallbackId> DiffCallbackById(
    oldItems: Collection<T>,
    newItems: Collection<T>,
    diffUtilContentCheck: DiffUtilContentCheck<T> = EqualsEquality<T>().diffUtilContentCheck()
): DiffUtil.Callback = DiffUtilDelegatingCallback(oldItems, newItems, DiffUtilIdentityCheckById(), diffUtilContentCheck)
