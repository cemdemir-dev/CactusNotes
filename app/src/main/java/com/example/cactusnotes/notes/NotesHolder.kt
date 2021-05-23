package com.example.cactusnotes.notes

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R

class NotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleText = itemView.findViewById<TextView>(R.id.noteTitle)
    var contentText = itemView.findViewById<TextView>(R.id.noteContent)
}