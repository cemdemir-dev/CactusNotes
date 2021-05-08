package com.example.cactusnotes.validation

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validate(validator: Validator): Boolean {
    val field = editText!!.text.toString()
    val errorMessage = validator.validate(field)

    return if (errorMessage == null) {
        error = null
        isErrorEnabled = false
        true
    } else {
        error = context.getString(errorMessage)
        false
    }
}