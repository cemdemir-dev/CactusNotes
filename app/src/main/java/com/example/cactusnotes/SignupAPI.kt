package com.example.cactusnotes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI {
    @POST("/auth/local/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}