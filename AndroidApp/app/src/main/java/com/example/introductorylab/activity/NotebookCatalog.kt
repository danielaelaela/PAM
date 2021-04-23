package com.example.introductorylab.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.R
import com.example.introductorylab.view.NotebookCatalogRecycleAdapter

class NotebookCatalog : AppCompatActivity() {

    private var notebookCatalogView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notebook_catalog)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Register notebook creation action on plus click
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, EditNotebook::class.java)
            startActivity(intent)
        }

        // Register reference to the last notebook section
        val lastNotebookSection = findViewById<ConstraintLayout?>(R.id.lastNotebookSection)
        lastNotebookSection?.visibility = View.GONE

        // Setup notebook list
        notebookCatalogView = findViewById(R.id.notebookList)
        notebookCatalogView?.layoutManager = LinearLayoutManager(this)
        notebookCatalogView?.adapter = NotebookCatalogRecycleAdapter(this, lastNotebookSection)
    }

    override fun onResume() {
        super.onResume()

        notebookCatalogView?.adapter?.notifyDataSetChanged()
    }
}