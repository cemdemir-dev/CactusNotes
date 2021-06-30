package com.example.cactusnotes.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name)
    }
}