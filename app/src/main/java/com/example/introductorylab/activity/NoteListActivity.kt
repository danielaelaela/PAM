package com.example.introductorylab.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.EXTRA_NOTE_ID
import com.example.introductorylab.domain.NoteRepository
import com.example.introductorylab.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Register handler for note creation button
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            openNoteEditor()
        }

        val noteList = findViewById<ListView>(R.id.noteList)
        // Setup auto update on the main page
        noteList.adapter = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, NoteRepository.notes)
        // Register handlers for note list link buttons
        noteList.setOnItemClickListener { _, _, position, _ ->
            val selectedNoteId = NoteRepository.notes[position].id
            openNoteEditor(selectedNoteId)
        }
    }

    private fun openNoteEditor(noteId: String = "") {
        val openNoteIntent = Intent(this, NoteEditActivity::class.java)
        openNoteIntent.putExtra(EXTRA_NOTE_ID, noteId)
        startActivity(openNoteIntent)
    }
}