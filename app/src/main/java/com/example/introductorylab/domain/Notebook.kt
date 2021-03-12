package com.example.introductorylab.domain

data class Notebook(override var id: String, var title: String, var description: String)
    : Entity {

    val notes: MutableList<Note> = ArrayList()

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun removeNote(note: Note) {
        notes.remove(note)
    }

    override fun toString(): String {
        return title
    }

    override fun equals(other: Any?): Boolean {
        return other is Note && id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}