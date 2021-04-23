package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.introductorylab.INTENT_PARAM_NOTEBOOK_ID
import com.example.introductorylab.R
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.service.NotebookService

class EditNotebook : AppCompatActivity() {

    private var notebookId: String = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notebook)

        notebookId = intent.getStringExtra(INTENT_PARAM_NOTEBOOK_ID).toString()

        NotebookService
                .findNotebookById(notebookId)
                ?.let { bindNotebookValues(it) }

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

        NotebookService.saveNotebook(notebookId, titleValue, descriptionValue)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun notebookNameView() = findViewById<TextView>(R.id.editNotebookName)
    private fun notebookDescriptionView() = findViewById<TextView>(R.id.editNotebookDescription)
}