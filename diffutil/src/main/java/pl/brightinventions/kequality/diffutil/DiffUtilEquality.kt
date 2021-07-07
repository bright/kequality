package pl.brightinventions.kequality.diffutil

import pl.brightinventions.kequality.Equality

class DiffUtilEqualityIdentityCheck<T>(private val equality: Equality<T>) : DiffUtilIdentityCheck<T> {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = equality.areEqual(oldItem, newItem)
}

class DiffUtilEqualityContentCheck<T>(private val equality: Equality<T>) : DiffUtilContentCheck<T> {
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = equality.areEqual(oldItem, newItem)
}

fun <T> Equality<T>.diffUtilIdentityCheck(): DiffUtilIdentityCheck<T> = DiffUtilEqualityIdentityCheck(this)
fun <T> Equality<T>.diffUtilContentCheck(): DiffUtilContentCheck<T> = DiffUtilEqualityContentCheck(this)
