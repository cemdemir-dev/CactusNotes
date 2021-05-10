package com.example.cactusnotes.userstore

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class UserStore(context: Context) {
    private val prefs = context.getSharedPreferences("user_store", MODE_PRIVATE)

    fun saveJwt(jwt: String) {
        prefs.edit(commit = true) {
            putString("jwt", jwt)
        }
    }

    fun loadJwt(): String? = prefs.getString("jwt", null)

    fun deleteJwt() {
        prefs.edit(commit = true) {
            remove("jwt")
        }
    }
}