package com.example.introductorylab.activity

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.introductorylab.INTENT_PARAM_NOTEBOOK_ID
import com.example.introductorylab.R
import com.example.introductorylab.fragment.NotesFragment
import com.example.introductorylab.service.NotebookService
import com.example.introductorylab.util.openEditNote
import com.example.introductorylab.util.openEditNotebook
import com.example.introductorylab.view.NoteCatalogRecycleAdapter
import com.example.introductorylab.view.NotebookViewPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class Notebook : AppCompatActivity() {

    private var notebook: com.example.introductorylab.domain.Notebook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notebook)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Configure tabs
        val pagerAdapter = NotebookViewPagerAdapter(supportFragmentManager)
        pagerAdapter.registerFragment("Active notes", NotesFragment.newInstance(getNotebookId(), false))
        pagerAdapter.registerFragment("Archived notes", NotesFragment.newInstance(getNotebookId(), true))
        val viewPager = findViewById<ViewPager>(R.id.notebookViewPager)
        viewPager?.adapter = pagerAdapter
        findViewById<TabLayout>(R.id.toolbarTabs)?.setupWithViewPager(viewPager)

        // Register creation of a new note on plus click
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            notebook?.id?.let { openEditNote(this, it) }
        }

        // Show back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        // Find data of the selected notebook
        notebook = NotebookService.findNotebookById(getNotebookId())

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
                notebook?.id?.let { NotebookService.deleteNotebookById(it) }
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getNotebookId(): String {
        return intent.getStringExtra(INTENT_PARAM_NOTEBOOK_ID).toString()
    }
}
