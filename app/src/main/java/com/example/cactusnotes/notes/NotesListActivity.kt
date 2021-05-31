package com.example.cactusnotes.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityNotesListBinding

class NotesListActivity : AppCompatActivity() {

    var stateIndex = LOADING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = NotesAdapter()
        binding.recyclerView.addItemDecoration(NoteItemDecoration())
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        updateUI(binding)

        binding.floatingActionButton.setOnClickListener {
            incrementState()
            updateUI(binding)
        }
    }

    private fun incrementState() {
        stateIndex += 1

        if (stateIndex == 4) {
            stateIndex = 0
        }
    }

    private fun updateUI(binding: ActivityNotesListBinding) {
        when (stateIndex) {
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