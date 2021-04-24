package com.example.cactusnotes

interface Validator {
    fun validate(field: String): Int?
}