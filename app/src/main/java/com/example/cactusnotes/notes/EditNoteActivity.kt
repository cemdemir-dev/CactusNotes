package com.example.cactusnotes.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.api.api
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.notes.data.NoteRequest
import com.example.cactusnotes.notes.data.NoteResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    var hasSentNoteRequest = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)

        binding.title.addTextChangedListener {
            if (!hasSentNoteRequest) {
                sendNoteRequest()
            }
            hasSentNoteRequest = true
        }
    }

    private fun sendNoteRequest() {
        setResult(RESULT_OK)

        val request = NoteRequest(
            binding.title.text.toString(),
            binding.content.text.toString()
        )

        api.createNote(request).enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {}

            override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    R.string.couldnt_connect_to_servers,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }
}