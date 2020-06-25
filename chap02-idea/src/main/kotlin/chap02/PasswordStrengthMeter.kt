package chap02

class PasswordStrengthMeter {
    fun meter(s: String): PasswordStrength? {
        if(s.isEmpty()) return PasswordStrength.INVALID
        val metCounts = getMetCriteriaCounts(s)
        if(metCounts <= 1) return PasswordStrength.WEAK
        if(metCounts == 2) return PasswordStrength.NORMAL
        return PasswordStrength.STRONG
    }

    private fun getMetCriteriaCounts(s: String): Int {
        var metCounts = 0
        if (s.length >= 8) metCounts++
        if (meetsContainingNumberCriteria(s)) metCounts++
        if (meetsContainingUppercaseCriteria(s)) metCounts++
        return metCounts
    }

    private fun meetsContainingUppercaseCriteria(s: String): Boolean {
        for (ch in s.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true
            }
        }
        return false
    }

    private fun meetsContainingNumberCriteria(s: String): Boolean {
        for (ch in s.toCharArray()) {
            if (ch in '0'..'9') {
                return true
            }
        }
        return false
    }
}
