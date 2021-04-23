package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.INTENT_PARAM_NOTEBOOK_ID
import com.example.introductorylab.INTENT_PARAM_NOTE_ID
import com.example.introductorylab.R
import com.example.introductorylab.domain.Note
import com.example.introductorylab.service.NotebookService

class EditNote : AppCompatActivity() {

    private var notebookId: String = ""
    private var noteId: String = ""

    // Workaround for problem occurring when you hit delete and onPause creates again deleted note
    private var deleteRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        notebookId = intent.getStringExtra(INTENT_PARAM_NOTEBOOK_ID) ?: ""
        noteId = intent.getStringExtra(INTENT_PARAM_NOTE_ID) ?: ""

        NotebookService
                .findNoteInNotebookById(notebookId, noteId)
                ?.let { bindNoteValues(it) }

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

        NotebookService.saveNoteInNotebook(notebookId, noteId, nameValue, contentValue)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionNoteDelete -> {
                NotebookService.deleteNoteFromNotebook(notebookId, noteId)
                deleteRequested = true
                finish()
                return true
            }
            R.id.actionNoteArchive -> {
                NotebookService.archiveNoteInNotebook(notebookId, noteId)
                finish()
                return true
            }
            R.id.actionNoteRestore -> {
                NotebookService.restoreNoteInNotebook(notebookId, noteId)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val note = NotebookService.findNoteInNotebookById(notebookId, noteId)

        val deleteMenuItem = menu?.findItem(R.id.actionNoteDelete)
        // Allow deleting a note only after it was saved
        deleteMenuItem?.isEnabled = noteId.isNotEmpty()

        val archiveMenuItem = menu?.findItem(R.id.actionNoteArchive)
        if (note?.archived == true) {
            menu?.removeItem(archiveMenuItem?.itemId ?: -1)
        }

        val restoreMenuItem = menu?.findItem(R.id.actionNoteRestore)
        if (note?.archived == false) {
            menu?.removeItem(restoreMenuItem?.itemId ?: -1)
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun noteNameView() = findViewById<TextView>(R.id.editNoteName)
    private fun noteContentView() = findViewById<TextView>(R.id.editNoteContent)
}