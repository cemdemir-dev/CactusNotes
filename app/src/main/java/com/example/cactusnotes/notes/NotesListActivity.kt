package com.example.cactusnotes.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.databinding.ActivityNotesListBinding

class NotesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = NotesAdapter()
        binding.recyclerView.addItemDecoration(NoteItemDecoration())
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }
}