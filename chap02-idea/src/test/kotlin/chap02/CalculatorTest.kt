package chap02

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CalculatorTest {

    @Test
    fun plus() {
        val result = Calculator.plus(1, 2)
        assertThat(3).isEqualTo(result)
        assertThat(5).isEqualTo(Calculator.plus(1, 4 ))
    }
}