package com.example.introductorylab.infrastructure

import com.example.introductorylab.domain.Note

object NoteRepository : InMemoryRepository<Note>() {
}