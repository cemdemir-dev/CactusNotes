package com.example.cactusnotes.api

import com.example.cactusnotes.userstore.UserStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userStore: UserStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = userStore.loadJwt()

        if (jwt == null || chain.request().url.pathSegments[0] == "auth") {
            return chain.proceed(chain.request())
        }

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $jwt")
            .build()

        return chain.proceed(request)
    }
}