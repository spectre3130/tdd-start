package chap03

import java.time.LocalDate

class PayData(
    val firstBillingDate: LocalDate? = null,
    val billingDate: LocalDate,
    val payAmount: Int
)
