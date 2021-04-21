package com.example.cactusnotes

class UsernameValidator : Validator{
    override fun validate(field: String) = when {
        field == "" -> R.string.username_is_required
        field.length < 3 -> R.string.username_too_short
        field.length > 19 -> R.string.username_too_long
        !field.all { it.isLowerCase() || it.isDigit() || it == '_' } -> R.string.username_can_only_include
        else -> null
    }

}


