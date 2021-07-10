package pl.brightinventions.kequality.android.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.brightinventions.kequality.ListEquality

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ManuallyDelegatingEqualityBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun compareCustomer1ToCustomer2() {
        benchmarkRule.measureRepeated {
            CustomerManuallyDelegatingEquality.areEqual(Customer1, Customer2)
        }
    }

    @Test
    fun compareCustomer3ToSelf() {
        benchmarkRule.measureRepeated {
            CustomerManuallyDelegatingEquality.areEqual(Customer3, Customer3)
        }
    }

    @Test
    fun compareListOf100Customer3ToSelf() {
        benchmarkRule.measureRepeated {
            ListEquality(CustomerManuallyDelegatingEquality, ignoreOrder = false).areEqual(
                Customer3Times100, Customer3Times100
            )
        }
    }
}