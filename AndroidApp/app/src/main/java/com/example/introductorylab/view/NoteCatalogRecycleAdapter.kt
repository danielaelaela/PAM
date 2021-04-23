package com.example.introductorylab.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.R
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.service.NotebookService
import com.example.introductorylab.util.cutString
import com.example.introductorylab.util.openEditNote

class NoteCatalogRecycleAdapter(private val context: Context, private val notebookId: String, private val archived: Boolean = false) :
    RecyclerView.Adapter<NoteCatalogRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_catalog_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = NotebookService.findNotesInNotebook(notebookId, archived)
        val note = if (position < items.size) items[position] else null
        note?.let { holder.bindNote(it) }
    }

    override fun getItemCount(): Int = NotebookService.findNotesInNotebook(notebookId, archived).size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteName = itemView.findViewById<TextView>(R.id.textNoteName)
        private val noteContent = itemView.findViewById<TextView>(R.id.textNoteContent)

        // Needed to find correct note when handling a click
        private var noteId: String = ""

        fun bindNote(note: Note) {
            noteName.text = cutString(note.title, 24)
            noteContent.text = cutString(note.content, 32)
            noteId = note.id

            itemView.setOnClickListener {
                openEditNote(context, notebookId, noteId)
            }
        }
    }
}
