package com.example.cactusnotes

class PasswordValidator {
    fun validate(password: String) = when {
        password.isBlank() -> R.string.password_is_required
        password.length < 8 -> R.string.password_too_short
        password.length > 39 -> R.string.password_too_long
        !password.containsLowerCase()
                || !password.containsDigit()
                || !password.containsUpperCase()
                || !password.containsSpecialChars() -> R.string.password_must_contain
        else -> null
    }

    private fun String.containsLowerCase() = any { it.isLowerCase() }

    private fun String.containsUpperCase() = any { it.isUpperCase() }

    private fun String.containsDigit() = any { it.isDigit() }

    private fun Char.isSpecial() = !isDigit() && !isLetter()

    private fun String.containsSpecialChars() = any { it.isSpecial() }
}