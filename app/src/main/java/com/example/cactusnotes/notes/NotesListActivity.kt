package com.example.cactusnotes.notes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.R
import com.example.cactusnotes.api.api
import com.example.cactusnotes.databinding.ActivityNotesListBinding
import com.example.cactusnotes.login.LoginActivity
import com.example.cactusnotes.notes.data.NoteResponse
import com.example.cactusnotes.userstore.UserStore
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotesListActivity : AppCompatActivity() {

    private var store = UserStore(this)

    lateinit var binding: ActivityNotesListBinding

    private val notesAdapter = NotesAdapter()

    private val startForResult =
        registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                fetchNotes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = notesAdapter
        binding.recyclerView.addItemDecoration(NoteItemDecoration())
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        fetchNotes()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startForResult.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_logout -> {
            logOut()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun fetchNotes() {
        updateUI(LOADING)

        api.readAllNotes().enqueue(object : Callback<List<NoteResponse>> {
            override fun onResponse(
                call: Call<List<NoteResponse>>,
                response: Response<List<NoteResponse>>
            ) {
                when (response.code()) {
                    200 -> onSuccessfulResponse(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<NoteResponse>>, t: Throwable) {
                updateUI(ERROR)
            }
        })

    }

    private fun onSuccessfulResponse(noteListResponse: List<NoteResponse>) {
        if (noteListResponse.isEmpty()) {
            updateUI(EMPTY)
        } else {
            updateUI(SUCCESS)
            notesAdapter.submitList(noteListResponse.mapToNotes())
        }
    }

    private fun List<NoteResponse>.mapToNotes() = map {
        Note(it.title, it.content)
    }

    private fun logOut() {
        store.deleteJwt()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateUI(state: Int) {
        when (state) {
            LOADING -> {
                binding.loadingIndicator.isVisible = true
                binding.recyclerView.isVisible = false
                binding.emptyText.isVisible = false
                binding.statusImage.isVisible = false

            }

            EMPTY -> {
                binding.loadingIndicator.isVisible = false
                binding.recyclerView.isVisible = false
                binding.emptyText.isVisible = true
                binding.statusImage.isVisible = true
                binding.statusImage.setImageResource(R.drawable.ic_cactus_green)
            }

            ERROR -> {
                binding.loadingIndicator.isVisible = false
                binding.recyclerView.isVisible = false
                binding.emptyText.isVisible = false
                binding.statusImage.isVisible = true
                binding.statusImage.setImageResource(R.drawable.ic_cactus_gray)
                Snackbar.make(
                    binding.root,
                    getString(R.string.couldnt_connect_to_servers), Snackbar.LENGTH_LONG
                ).show()
            }

            SUCCESS -> {
                binding.loadingIndicator.isVisible = false
                binding.recyclerView.isVisible = true
                binding.emptyText.isVisible = false
                binding.statusImage.isVisible = false
            }
        }
    }

    companion object {
        const val LOADING = 0
        const val EMPTY = 1
        const val ERROR = 2
        const val SUCCESS = 3
    }
}