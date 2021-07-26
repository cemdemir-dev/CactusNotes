package com.example.cactusnotes.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R

class NotesAdapter(val noteClickListener: (Note) -> Unit) : RecyclerView.Adapter<NotesHolder>() {
    private var notes: List<Note> = listOf()

    fun submitList(noteList: List<Note>) {
        notes = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val itemNote =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesHolder(itemNote)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val note = notes[position]
        holder.titleText.text = note.title
        holder.contentText.text = note.content

        holder.itemView.setOnClickListener {
            noteClickListener(note)
        }
    }

    override fun getItemCount() = notes.size

}