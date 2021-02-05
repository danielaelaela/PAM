package com.example.introductorylab.domain

data class Note(val id: String, var title: String, var content: String) {
    override fun toString(): String {
        return title
    }

    override fun equals(other: Any?): Boolean {
        return other is Note && id == other.id
    }
}