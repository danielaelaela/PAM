package com.example.introductorylab.service

import com.example.introductorylab.API_ROOT
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.infrastructure.NoteDto
import com.example.introductorylab.infrastructure.NotebookDto
import com.example.introductorylab.infrastructure.NotebookGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotebookService {

    private val notebookGateway: NotebookGateway = buildGateway()

    private fun buildGateway(): NotebookGateway {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(NotebookGateway::class.java)
    }

    fun findAllNotebooks(): List<Notebook> {
        return runBlocking(Dispatchers.IO) {
            val response = notebookGateway.getAllNotebooks().execute()
            return@runBlocking response.body().map { it.mapToDomain() }
        }
    }

    fun findNotebookById(notebookId: String): Notebook? {
        return runBlocking(Dispatchers.IO) {
            val response = notebookGateway.getNotebookById(notebookId).execute()
            return@runBlocking if (response.isSuccessful) response.body().mapToDomain() else null
        }
    }

    fun createNotebook(name: String, description: String): Notebook {
        return runBlocking(Dispatchers.IO) {
            val notebookDto = NotebookDto(name = name, description = description)
            val response = notebookGateway.createNotebook(notebookDto).execute()
            return@runBlocking response.body().mapToDomain()
        }
    }

    fun updateNotebook(notebookId: String, name: String, description: String): Notebook {
        return runBlocking(Dispatchers.IO) {
            val notebookDto = NotebookDto(notebookId, name, description)
            val response = notebookGateway.updateNotebook(notebookId, notebookDto).execute()
            return@runBlocking response.body().mapToDomain()
        }
    }

    fun saveNotebook(notebookId: String, name: String, description: String): Notebook {
        return if (findNotebookById(notebookId) == null) {
            createNotebook(name, description)
        } else {
            updateNotebook(notebookId, name, description)
        }
    }

    fun deleteNotebookById(notebookId: String) {
        runBlocking(Dispatchers.IO) {
            notebookGateway.deleteNotebookById(notebookId).execute()
        }
    }

    fun findNotesInNotebook(notebookId: String, archived: Boolean = false): List<Note> {
        return runBlocking(Dispatchers.IO) {
            val response = notebookGateway.getNotesFromNotebook(notebookId, archived).execute()
            return@runBlocking response.body().map { it.mapToDomain() }
        }
    }

    fun findNoteInNotebookById(notebookId: String, noteId: String): Note? {
        return runBlocking(Dispatchers.IO) {
            val response = notebookGateway.getNoteFromNotebookById(notebookId, noteId).execute()
            return@runBlocking if (response.isSuccessful) response.body().mapToDomain() else null
        }
    }

    fun createNoteInNotebook(notebookId: String, noteName: String, noteContent: String): Note {
        return runBlocking(Dispatchers.IO) {
            val noteDto = NoteDto(name = noteName, content = noteContent)
            val response = notebookGateway.createNoteInNotebook(notebookId, noteDto).execute()
            return@runBlocking response.body().mapToDomain()
        }
    }

    fun updateNoteInNotebook(notebookId: String, noteId: String, noteName: String, noteContent: String): Note {
        return runBlocking(Dispatchers.IO) {
            val noteDto = NoteDto(noteId, noteName, noteContent)
            val response = notebookGateway.updateNoteInNotebook(notebookId, noteId, noteDto).execute()
            return@runBlocking response.body().mapToDomain()
        }
    }

    fun saveNoteInNotebook(notebookId: String, noteId: String, noteName: String, noteContent: String): Note {
        return if (findNoteInNotebookById(notebookId, noteId) == null) {
            createNoteInNotebook(notebookId, noteName, noteContent)
        } else {
            updateNoteInNotebook(notebookId, noteId, noteName, noteContent)
        }
    }

    fun archiveNoteInNotebook(notebookId: String, noteId: String) {
        runBlocking(Dispatchers.IO) {
            notebookGateway.archiveNoteInNotebook(notebookId, noteId).execute()
        }
    }

    fun restoreNoteInNotebook(notebookId: String, noteId: String) {
        runBlocking(Dispatchers.IO) {
            notebookGateway.restoreNoteInNotebook(notebookId, noteId).execute()
        }
    }

    fun deleteNoteFromNotebook(notebookId: String, noteId: String) {
        runBlocking(Dispatchers.IO) {
            notebookGateway.deleteNoteFromNotebookById(notebookId, noteId).execute()
        }
    }

    private fun NotebookDto.mapToDomain(): Notebook {
        val notebook = Notebook(id, name, description)
        notes.map { it.mapToDomain() }.forEach(notebook::addNote)
        return notebook
    }

    private fun NoteDto.mapToDomain(): Note {
        return Note(id, name, content, archived)
    }
}