package chap03

import java.time.LocalDate
import java.time.YearMonth

class ExpiryDateCalculator {

    fun calculateExpiryDate(payData: PayData): LocalDate {
        val addedMonths = if(payData.payAmount == 100_000) 12 else payData.payAmount / 10_000L
        return if(payData.firstBillingDate != null) {
            expiryDateUsingFirstBillingDate(payData, addedMonths) // 함수로 넘어가면 null 추론이 안됨
        } else {
            payData.billingDate.plusMonths(addedMonths)
        }
    }

    private fun expiryDateUsingFirstBillingDate(payData: PayData, addedMonths: Long): LocalDate {
        val candidateExp = payData.billingDate.plusMonths(addedMonths)
        val dayOfFirstBilling = payData.firstBillingDate!!.dayOfMonth //코틀린에서 리팩토링은 ??
        if (isSameDayOfMonth(dayOfFirstBilling, candidateExp)) {
            val dayLenOfCandiMon = lastDayOfMonth(candidateExp)
            if (dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon)
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling)
        } else {
            return candidateExp
        }
    }

    private fun lastDayOfMonth(candidateExp: LocalDate) = YearMonth.from(candidateExp).lengthOfMonth()

    private fun isSameDayOfMonth(dayOfFirstBilling: Int, candidateExp: LocalDate) = dayOfFirstBilling != candidateExp.dayOfMonth
}
