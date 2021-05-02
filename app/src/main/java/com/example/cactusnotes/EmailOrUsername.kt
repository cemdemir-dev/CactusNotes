package com.example.cactusnotes

class EmailOrUsername : Validator {
    override fun validate(field: String) = when {
        field == "" -> R.string.e_mail_or_username
        else -> null
    }
}