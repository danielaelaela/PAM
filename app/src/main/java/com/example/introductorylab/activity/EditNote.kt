package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.NOTEBOOK_ID
import com.example.introductorylab.NOTE_ID
import com.example.introductorylab.R
import com.example.introductorylab.domain.Note
import com.example.introductorylab.infrastructure.NoteRepository
import com.example.introductorylab.infrastructure.NotebookRepository

class EditNote : AppCompatActivity() {

    private var notebookId: String = ""
    private var noteId: String = ""

    // Workaround for problem occurring when you hit delete and onPause creates again deleted note
    private var deleteRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        notebookId = intent.getStringExtra(NOTEBOOK_ID) ?: ""
        noteId = intent.getStringExtra(NOTE_ID) ?: ""

        val note = NoteRepository.findById(noteId)
        if (note != null) {
            bindNoteValues(note)
        }

        // Show back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindNoteValues(note: Note) {
        noteNameView().text = note.title
        noteContentView().text = note.content
        noteId = note.id

        supportActionBar?.title = note.title
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onPause() {
        super.onPause()

        if (deleteRequested) {
            return
        }

        val nameValue = noteNameView().text.toString()
        val contentValue = noteContentView().text.toString()

        var note = NoteRepository.findById(noteId)
        if (note != null) {
            note.title = nameValue
            note.content = contentValue
        } else {
            val newNote = Note("", nameValue, contentValue)
            NotebookRepository.findById(notebookId)?.addNote(newNote)
            note = newNote
        }
        NoteRepository.save(note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionNoteDelete -> {
                // TODO Introduce service layer to separate data management
                val note = NoteRepository.findById(noteId)
                if (note != null) {
                    val notebook = NotebookRepository.findById(notebookId)
                    notebook?.removeNote(note)
                }
                NoteRepository.deleteById(noteId)
                deleteRequested = true
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val deleteMenuItem = menu?.findItem(R.id.actionNoteDelete)
        // Allow deleting a note only after it was saved
        deleteMenuItem?.isEnabled = noteId.isNotEmpty()

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun noteNameView() = findViewById<TextView>(R.id.editNoteName)
    private fun noteContentView() = findViewById<TextView>(R.id.editNoteContent)
}