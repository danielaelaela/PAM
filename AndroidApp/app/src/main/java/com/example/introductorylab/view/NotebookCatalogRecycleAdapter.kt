package com.example.introductorylab.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.R
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.service.NotebookService
import com.example.introductorylab.util.cutString
import com.example.introductorylab.util.openNotebook

class NotebookCatalogRecycleAdapter(private val context: Context, private val lastNotebookSection: ConstraintLayout?) :
    RecyclerView.Adapter<NotebookCatalogRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_catalog_notebook, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = NotebookService.findAllNotebooks()
        val notebook = if (position < items.size) items[position] else null
        notebook?.let { holder.bindNotebook(it) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getItemCount(): Int = NotebookService.findAllNotebooks().size

    private fun setLastNotebook(notebook: Notebook) {
        lastNotebookSection?.visibility = View.VISIBLE
        lastNotebookSection?.findViewById<TextView?>(R.id.textNotebookName)?.text = cutString(notebook.title, 32)
        lastNotebookSection?.findViewById<TextView?>(R.id.textNotebookDescription)?.text = cutString(notebook.description)
        lastNotebookSection?.setOnClickListener {
            openNotebook(context, notebook.id)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notebookName = itemView.findViewById<TextView>(R.id.textNotebookName)
        private val notebookDescription = itemView.findViewById<TextView>(R.id.textNotebookDescription)

        fun bindNotebook(notebook: Notebook) {
            notebookName.text = cutString(notebook.title, 32)
            notebookDescription.text = cutString(notebook.description)

            itemView.setOnClickListener {
                setLastNotebook(notebook)
                openNotebook(context, notebook.id)
            }
        }
    }
}
