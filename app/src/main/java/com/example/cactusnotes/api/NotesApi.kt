package com.example.cactusnotes.api

import com.example.cactusnotes.login.data.LoginRequest
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.notes.data.NoteRequest
import com.example.cactusnotes.notes.data.NoteResponse
import com.example.cactusnotes.signup.data.RegisterRequest
import com.example.cactusnotes.signup.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface NotesApi {
    @POST("/auth/local/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/auth/local")
    fun login(@Body LoginRequest: LoginRequest): Call<LoginResponse>

    @GET("/notes")
    fun readAllNotes(): Call<List<NoteResponse>>

    @POST("/notes")
    fun createNote(@Body createNoteRequest: NoteRequest): Call<NoteResponse>

    @PUT("/notes/{noteId}")
    fun editNote(
        @Body editNoteRequest: NoteRequest,
        @Path("noteId") noteId: Int
    ): Call<NoteResponse>

    @DELETE("/notes/{noteId}")
    fun deleteNote(@Path("noteId") noteId: Int): Call<NoteResponse>

}