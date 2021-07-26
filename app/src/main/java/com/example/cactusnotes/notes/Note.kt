package com.example.cactusnotes.notes

import java.io.Serializable

data class Note(
    val id: Int,
    val title: String,
    val content: String
) : Serializable