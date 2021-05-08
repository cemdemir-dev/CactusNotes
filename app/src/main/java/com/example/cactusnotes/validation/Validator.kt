package com.example.cactusnotes.validation

interface Validator {
    fun validate(field: String): Int?
}