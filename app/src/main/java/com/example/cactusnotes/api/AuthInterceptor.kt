package com.example.cactusnotes.api

import com.example.cactusnotes.userstore.UserStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val jwt = "ghvbjknlşjhbgvbjknlmkmgh"

        val store = UserStore()

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $jwt")
            .build()

        return chain.proceed(request)
    }
}