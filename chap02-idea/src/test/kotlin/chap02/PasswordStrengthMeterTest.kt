package chap02

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PasswordStrengthMeterTest {
    private val meter = PasswordStrengthMeter()

    @Test
    fun meetsAllCriteria_Then_Strong() {
        val result1 = meter.meter("ab12!@AB")
        val result2 = meter.meter("abc1!Add")

        assertThat(PasswordStrength.STRONG).isEqualTo(result1)
        assertThat(PasswordStrength.STRONG).isEqualTo(result2)
    }
    
    @Test 
    fun meetsOtherCriteria_except_for_Length_Then_Normal() {
        val result1 = meter.meter("ab12!@A")
        val result2 = meter.meter("Ab12!c")

        assertThat(PasswordStrength.NORMAL).isEqualTo(result1)
        assertThat(PasswordStrength.NORMAL).isEqualTo(result2)
    }
    
    @Test 
    fun meetsOtherCriteria_except_for_number_Then_Normal() {
        val result = meter.meter("ab!@ABqwer")

        assertThat(PasswordStrength.NORMAL).isEqualTo(result)
    }
    
    @Test 
    fun emptyInput_Then_Invalid() {
        val result = meter.meter("")

        assertThat(PasswordStrength.INVALID).isEqualTo(result)
    }
    
    @Test 
    fun meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        val result = meter.meter("ab12!@df")

        assertThat(PasswordStrength.NORMAL).isEqualTo(result)
    }
    
    @Test 
    fun meetsOnlyLengthCriteria_Then_Weak() {
        val result = meter.meter("abdefghi")

        assertThat(PasswordStrength.WEAK).isEqualTo(result)
    }
    
    @Test 
    fun meetsOnlyNumCriteria_Then_Weak() {
        val result = meter.meter("12345")

        assertThat(PasswordStrength.WEAK).isEqualTo(result)
    }
    
    @Test 
    fun meetsOnlyUpperCriteria_Then_Weak() {
        val result = meter.meter("ABZEF")

        assertThat(PasswordStrength.WEAK).isEqualTo(result)
    }
    
    @Test
    fun meetsNoCriteria_Then_Weak() {
        val result = meter.meter("abc")

        assertThat(PasswordStrength.WEAK).isEqualTo(result)
    }

    
}