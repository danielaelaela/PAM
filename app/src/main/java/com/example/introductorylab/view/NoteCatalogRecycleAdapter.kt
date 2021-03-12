package com.example.introductorylab.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.introductorylab.NOTEBOOK_ID
import com.example.introductorylab.NOTE_ID
import com.example.introductorylab.R
import com.example.introductorylab.activity.EditNote
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.infrastructure.NoteRepository
import com.example.introductorylab.util.cutString
import com.example.introductorylab.util.openEditNote

class NoteCatalogRecycleAdapter(private val context: Context, private val notebook: Notebook) :
    RecyclerView.Adapter<NoteCatalogRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_catalog_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notebook.notes[position]
        holder.bindNote(note)
    }

    override fun getItemCount(): Int = notebook.notes.size

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
                openEditNote(context, notebook.id, noteId)
            }
        }
    }
}
