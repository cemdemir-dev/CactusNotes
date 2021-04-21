package com.example.cactusnotes

class EmailValidator : Validator {
    override fun validate(field: String) = when {
        field == "" -> R.string.e_mail_required
        field.length < 6 -> R.string.e_mail_invalid
        field.length > 49 -> R.string.e_mail_invalid
        !field.contains("@") && !field.contains(".") -> R.string.e_mail_invalid
        !field.contains(".") -> R.string.e_mail_invalid
        else -> null
    }
}