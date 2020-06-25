package chap03

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ExpiryDateCalculatorTest {

    private val cal = ExpiryDateCalculator()

    @Test
    fun `만원 납부하면 한달 뒤가 만료일이 됨`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 4, 1)
        )
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 4, 1),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 5, 1)
        )
    }
    
    @Test 
    fun `납부일과 한달 뒤 일자가 같지 않음`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 31),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 2, 28)
        )
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 5, 31),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 6, 30)
        )
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2020, 1, 30),
                payAmount = 10_000
            ),
            LocalDate.of(2020, 2, 29)
        )
    }
    
    @Test 
    fun `첫 납부일과 만료일 일자가 다를때 만원 납부`() {
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 3, 31)
        )
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 30),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 3, 30)
        )
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 5, 31),
                billingDate = LocalDate.of(2019, 6, 30),
                payAmount = 10_000
            ),
            LocalDate.of(2019, 7, 31)
        )
    }

    @Test
    fun `이만원 이상 납부하면 비례해서 만료일 계산`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 3, 1),
                payAmount = 20_000
            ),
            LocalDate.of(2019, 5 ,1 )
        )
        assertExpiryDate(
            PayData(
                    billingDate = LocalDate.of(2019, 3, 1),
                    payAmount = 30_000
            ),
            LocalDate.of(2019, 6 ,1)
        )
    }
    
    @Test 
    fun `첫 납부일과 만료일 일자가 다를때 이만원 이상 납부`() {
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 20_000
            ),
            LocalDate.of(2019, 4 ,30)
        )
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 1, 31),
                billingDate = LocalDate.of(2019, 2, 28),
                payAmount = 40_000
            ),
            LocalDate.of(2019, 6 ,30)
        )
        assertExpiryDate(
            PayData(
                firstBillingDate = LocalDate.of(2019, 3, 31),
                billingDate = LocalDate.of(2019, 4, 30),
                payAmount = 30_000
            ),
            LocalDate.of(2019, 7 ,31)
        )
    }
    
    @Test 
    fun `십만원을 납부하면 1년 제공`() {
        assertExpiryDate(
            PayData(
                billingDate = LocalDate.of(2019, 1, 28),
                payAmount = 100_000
            ),
            LocalDate.of(2020, 1,28)
        )
    }
    
    

    //검증하고 싶은 내용을 알아보기 힘들 수 있기 때문에 테스트 중복 제거는 고민 해볼 필요가 있음
    private fun assertExpiryDate(payData: PayData, expectedExpiryDate: LocalDate) {
        val expiryDate = cal.calculateExpiryDate(payData)
        assertThat(expectedExpiryDate).isEqualTo(expiryDate)
    }

}