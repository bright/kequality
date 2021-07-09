package pl.brightinventions.kequality.android.benchmark

import org.junit.Test
import pl.miensol.shouldko.shouldEqual

class CustomerEqualityTest {

    @Test
    fun compareCustomer1ToSelf() {
        CustomerPropertyEquality.areEqual(Customer1, Customer1).shouldEqual(true)
        CustomerManualEquality.areEqual(Customer1, Customer1).shouldEqual(true)
        CustomerDelegatingEquality.areEqual(Customer1, Customer1).shouldEqual(true)
    }

    @Test
    fun compareCustomer2ToSelf() {
        CustomerPropertyEquality.areEqual(Customer2, Customer2).shouldEqual(true)
        CustomerManualEquality.areEqual(Customer2, Customer2).shouldEqual(true)
        CustomerDelegatingEquality.areEqual(Customer2, Customer2).shouldEqual(true)
    }

    @Test
    fun compareCustomer3ToSelf() {
        CustomerPropertyEquality.areEqual(Customer3, Customer3).shouldEqual(true)
        CustomerManualEquality.areEqual(Customer3, Customer3).shouldEqual(true)
        CustomerDelegatingEquality.areEqual(Customer3, Customer3).shouldEqual(true)
    }

    @Test
    fun compareCustomer1ToCustomer2() {
        CustomerPropertyEquality.areEqual(Customer1, Customer2).shouldEqual(false)
        CustomerManualEquality.areEqual(Customer1, Customer2).shouldEqual(false)
        CustomerDelegatingEquality.areEqual(Customer1, Customer2).shouldEqual(false)
    }

    @Test
    fun compareCustomer1ToCustomer3() {
        CustomerPropertyEquality.areEqual(Customer1, Customer3).shouldEqual(false)
        CustomerManualEquality.areEqual(Customer1, Customer3).shouldEqual(false)
    }

    @Test
    fun compareCustomer3ToCustomer2() {
        CustomerPropertyEquality.areEqual(Customer3, Customer2).shouldEqual(false)
        CustomerManualEquality.areEqual(Customer3, Customer2).shouldEqual(false)
    }
}