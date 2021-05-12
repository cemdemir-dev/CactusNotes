package com.example.cactusnotes.api

import android.content.Context
import com.example.cactusnotes.userstore.UserStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val api: NotesApi get() = _api!!

private var _api: NotesApi? = null

fun generateApi(context: Context) {
    if (_api != null) return

    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(BODY)
    }

    val userStore = UserStore(context)

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(AuthInterceptor(userStore))
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()

    _api = Retrofit.Builder()
        .baseUrl("https://apps.cactus.school")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NotesApi::class.java)
}