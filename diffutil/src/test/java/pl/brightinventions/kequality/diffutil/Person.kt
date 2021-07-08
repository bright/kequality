package pl.brightinventions.kequality.diffutil

import pl.brightinventions.kequality.CompositeEquality
import pl.brightinventions.kequality.comparableEquality
import pl.brightinventions.kequality.equalsEquality
import java.math.BigDecimal

data class Person(val id: Long, val name: String, val age: BigDecimal) : HasDiffCallbackId {
    override val diffCallbackId: Any
        get() = id
}

val PersonEquality = CompositeEquality(
    Person::id.equalsEquality,
    Person::name.equalsEquality,
    Person::age.comparableEquality
)
