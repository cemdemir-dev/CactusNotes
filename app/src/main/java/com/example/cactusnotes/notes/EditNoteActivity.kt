package com.example.cactusnotes.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.api.api
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.notes.EditNoteActivity.NoteState.*
import com.example.cactusnotes.notes.data.NoteRequest
import com.example.cactusnotes.notes.data.NoteResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    private var noteState = NOT_CREATED

    private var note: NoteResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)

        binding.title.addTextChangedListener {
            onTextChanged()
        }

        binding.content.addTextChangedListener {
            onTextChanged()
        }
    }

    private fun onTextChanged() {
        when (noteState) {
            NOT_CREATED -> {
                sendCreateNoteRequest()
                noteState = IS_CREATING
            }
            CREATED -> {
                scheduleEditNoteRequest()
            }
        }
    }

    private fun scheduleEditNoteRequest() {
        val request = NoteRequest(
            title = binding.title.text.toString(),
            content = binding.content.text.toString()
        )

        Handler(Looper.getMainLooper()).postDelayed({
            sendEditNoteRequest(request)
        }, 300)
    }

    private fun sendEditNoteRequest(request: NoteRequest) {
        val title = binding.title.text.toString()
        val content = binding.content.text.toString()

        if (request.title != title || request.content != content) return

        api.editNote(request, note!!.id).enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {
                note = response.body()!!
            }

            override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    R.string.couldnt_connect_to_servers,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun sendCreateNoteRequest() {
        setResult(RESULT_OK)

        val request = NoteRequest(
            binding.title.text.toString(),
            binding.content.text.toString()
        )

        api.createNote(request).enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {
                if (response.isSuccessful) {
                    noteState = CREATED
                    note = response.body()!!

                    val title = binding.title.text.toString()
                    val content = binding.content.text.toString()

                    if (note!!.title != title || note!!.content != content) {
                        scheduleEditNoteRequest()
                    }
                } else {
                    noteState = NOT_CREATED
                }
            }

            override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                noteState = NOT_CREATED

                Snackbar.make(
                    binding.root,
                    R.string.couldnt_connect_to_servers,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    enum class NoteState {
        NOT_CREATED,
        IS_CREATING,
        CREATED
    }
}