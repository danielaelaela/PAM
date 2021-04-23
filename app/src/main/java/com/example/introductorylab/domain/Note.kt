package com.example.introductorylab.domain

data class Note(override var id: String, var title: String, var content: String)
    : Entity {

    override fun toString(): String {
        return title
    }

    override fun equals(other: Any?): Boolean {
        return other is Note && id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}