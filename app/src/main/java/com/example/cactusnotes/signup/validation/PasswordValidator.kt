package com.example.cactusnotes.signup.validation

import com.example.cactusnotes.R
import com.example.cactusnotes.validation.Validator

class PasswordValidator : Validator {
    override fun validate(field: String) = when {
        field.isBlank() -> R.string.password_is_required
        field.length < 8 -> R.string.password_too_short
        field.length > 39 -> R.string.password_too_long
        !field.containsLowerCase()
                || !field.containsDigit()
                || !field.containsUpperCase()
                || !field.containsSpecialChars() -> R.string.password_must_contain
        else -> null
    }

    private fun String.containsLowerCase() = any { it.isLowerCase() }

    private fun String.containsUpperCase() = any { it.isUpperCase() }

    private fun String.containsDigit() = any { it.isDigit() }

    private fun Char.isSpecial() = !isDigit() && !isLetter()

    private fun String.containsSpecialChars() = any { it.isSpecial() }
}