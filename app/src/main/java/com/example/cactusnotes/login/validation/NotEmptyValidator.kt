package com.example.cactusnotes.login.validation

import com.example.cactusnotes.validation.Validator

class NotEmptyValidator(private val errorMessageResId: Int) : Validator {
    override fun validate(field: String) = when {
        field == "" -> errorMessageResId
        else -> null
    }
}