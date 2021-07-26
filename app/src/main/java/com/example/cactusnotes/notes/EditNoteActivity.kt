package com.example.cactusnotes.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.api.api
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.notes.EditNoteActivity.NoteState.*
import com.example.cactusnotes.notes.data.NoteRequest
import com.example.cactusnotes.notes.data.NoteResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        val noteFromIntent: Note? = intent.getSerializableExtra("note") as Note?

        if (noteFromIntent != null) {
            noteState = CREATED
            note = noteFromIntent.toNoteResponse()

            binding.title.setText(note!!.title)
            binding.content.setText(note!!.content)
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
        setResult(RESULT_OK)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete -> {
            showDeleteDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage(R.string.this_action_is_irreversible)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.delete) { _, _ ->
                sendDeleteNoteRequest()
            }
            .show()
    }

    private fun sendDeleteNoteRequest() {
        api.deleteNote(note!!.id).enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {
                val noteTitle = response.body()!!.title
                val message = getString(R.string.your_note_is_deleted, noteTitle)
                Toast.makeText(this@EditNoteActivity, message, Toast.LENGTH_LONG).show()

                setResult(RESULT_OK)
                finish()
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

    enum class NoteState {
        NOT_CREATED,
        IS_CREATING,
        CREATED
    }

    private fun Note.toNoteResponse() = NoteResponse(
        id = id,
        title = title,
        content = content
    )
}