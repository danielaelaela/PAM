package com.example.introductorylab.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.EXTRA_NOTE_ID
import com.example.introductorylab.R
import com.example.introductorylab.R.id.noteEditSaveButton
import com.example.introductorylab.R.id.noteContent
import com.example.introductorylab.R.id.noteTitle
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.NoteRepository
import java.util.*

class NoteEditActivity : AppCompatActivity() {

    var noteTitleView: TextView? = null
    var noteContentView: TextView? = null
    var noteSaveButton: Button? = null

    var noteId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        noteId = intent.getStringExtra(EXTRA_NOTE_ID).orEmpty()
        val existingNote: Note? = NoteRepository.findById(noteId)

        noteTitleView = findViewById(noteTitle)
        noteContentView = findViewById(noteContent)
        noteSaveButton = findViewById(noteEditSaveButton)

        if (existingNote != null) {
            noteTitleView?.text = existingNote.title
            noteContentView?.text = existingNote.content
        } else {
            noteId = UUID.randomUUID().toString()
        }

        // Register handler for save button
        noteSaveButton?.setOnClickListener { _ ->
            val title = noteTitleView?.text
            val content = noteContentView?.text
            NoteRepository.saveNote(Note(noteId, title.toString(), content.toString()))

            finish()
        }
    }
}