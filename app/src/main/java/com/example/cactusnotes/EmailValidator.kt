package com.example.cactusnotes

class EmailValidator {
    fun validate(email: String) = when {
        email == "" -> R.string.e_mail_required
        email.length < 6 -> R.string.e_mail_invalid
        email.length > 49 -> R.string.e_mail_invalid
        !email.contains("@") && !email.contains(".") -> R.string.e_mail_invalid
        !email.contains(".") -> R.string.e_mail_invalid
        else -> null
    }
}