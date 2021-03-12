package com.example.introductorylab.domain

object NoteRepository {
    val notes: MutableList<Note> = ArrayList()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        saveNote(Note("1", "My First Note - README", "Write your notes here"))
    }

    fun findAll(): List<Note> {
        return notes.toList()
    }

    fun findById(id: String): Note? {
        return notes.find { note -> note.id == id }
    }

    fun saveNote(note: Note): Note {
        notes.remove(note)
        notes.add(note)
        return note
    }
}