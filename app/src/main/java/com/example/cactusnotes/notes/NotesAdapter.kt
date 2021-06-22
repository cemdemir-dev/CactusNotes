package com.example.cactusnotes.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R
import com.example.cactusnotes.notes.data.NoteResponse

class NotesAdapter : RecyclerView.Adapter<NotesHolder>() {
    private var notes: List<Note> = listOf()

    fun submitList(noteList: List<NoteResponse>) {
        notes = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val itemNote =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesHolder(itemNote)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val n = notes[position]
        holder.titleText.text = n.title
        holder.contentText.text = n.content
    }

    override fun getItemCount() = notes.size

}