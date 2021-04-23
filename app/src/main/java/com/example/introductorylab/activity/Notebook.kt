package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.NOTEBOOK_ID
import com.example.introductorylab.R
import com.example.introductorylab.infrastructure.NotebookRepository
import com.example.introductorylab.util.openEditNote
import com.example.introductorylab.util.openEditNotebook
import com.example.introductorylab.view.NoteCatalogRecycleAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Notebook : AppCompatActivity() {

    private var noteCatalogView: RecyclerView? = null

    private var notebook: com.example.introductorylab.domain.Notebook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notebook)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Register creation of a new note on plus click
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            notebook?.id?.let { openEditNote(this, it) }
        }

        // Find data of the selected notebook
        val notebookId = intent.getStringExtra(NOTEBOOK_ID).toString()
        notebook = NotebookRepository.findById(notebookId)

        // Setup note list
        noteCatalogView = findViewById(R.id.noteList)
        noteCatalogView?.layoutManager = GridLayoutManager(this, 2)
        noteCatalogView?.adapter = NoteCatalogRecycleAdapter(this, notebook!!)

        // Show back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        noteCatalogView?.adapter?.notifyDataSetChanged()
        supportActionBar?.title = notebook?.title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notebook, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionNotebookEdit -> {
                notebook?.id?.let { openEditNotebook(this, it) }
                return true
            }
            R.id.actionNotebookDelete -> {
                notebook?.id?.let { NotebookRepository.deleteById(it) }
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val archiveMenuItem = menu?.findItem(R.id.actionNotebookArchive)
        archiveMenuItem?.isEnabled = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
