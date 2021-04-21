package com.example.cactusnotes

class UsernameValidator {
    fun validate(username: String) = when {
        username == "" -> R.string.username_is_required
        username.length < 3 -> R.string.username_too_short
        username.length > 19 -> R.string.username_too_long
        !username.all { it.isLowerCase() || it.isDigit() || it == '_' } -> R.string.username_can_only_include
        else -> null
    }

}


