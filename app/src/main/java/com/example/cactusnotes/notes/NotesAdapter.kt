package com.example.cactusnotes.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R

class NotesAdapter : RecyclerView.Adapter<NotesHolder>() {
    private val notes: List<Note> = listOf(
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
        Note("Kapı kolu", "Tamir edilecek."),
        Note("Ödev", "Recyclerview ödevini unutma"),
        Note("Market Eksikler", "meyve, sebze, peynir, meyve, sebze, peynir,".repeat(30)),
    )

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