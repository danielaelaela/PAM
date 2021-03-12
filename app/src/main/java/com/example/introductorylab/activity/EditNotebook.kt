package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.NOTEBOOK_ID
import com.example.introductorylab.R
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.infrastructure.NoteRepository
import com.example.introductorylab.infrastructure.NotebookRepository

class EditNotebook : AppCompatActivity() {

    private var notebookId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notebook)

        notebookId = intent.getStringExtra(NOTEBOOK_ID).toString()

        val notebook = NotebookRepository.findById(notebookId)
        if (notebook != null) {
            bindNotebookValues(notebook)
        }

        // Show back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindNotebookValues(notebook: Notebook) {
        notebookNameView().text = notebook.title
        notebookDescriptionView().text = notebook.description
        supportActionBar?.title = notebook.title
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onPause() {
        super.onPause()

        val titleValue = notebookNameView().text.toString()
        val descriptionValue = notebookDescriptionView().text.toString()

        var notebook = NotebookRepository.findById(notebookId)
        if (notebook != null) {
            notebook.title = titleValue
            notebook.description = descriptionValue
        } else {
            val newNotebook = Notebook("", titleValue, descriptionValue)
            notebook = newNotebook
        }
        NotebookRepository.save(notebook)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun notebookNameView() = findViewById<TextView>(R.id.editNotebookName)
    private fun notebookDescriptionView() = findViewById<TextView>(R.id.editNotebookDescription)
}