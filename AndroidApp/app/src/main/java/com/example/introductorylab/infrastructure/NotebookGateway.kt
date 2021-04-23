package com.example.introductorylab.infrastructure

import retrofit2.Call
import retrofit2.http.*

interface NotebookGateway {

    @GET("notebooks")
    fun getAllNotebooks(): Call<List<NotebookDto>>

    @GET("notebooks/{notebookId}")
    fun getNotebookById(@Path("notebookId") notebookId: String): Call<NotebookDto>

    @POST("notebooks")
    fun createNotebook(@Body notebookDto: NotebookDto): Call<NotebookDto>

    @PUT("notebooks/{notebookId}")
    fun updateNotebook(@Path("notebookId") notebookId: String, @Body notebookDto: NotebookDto): Call<NotebookDto>

    @DELETE("notebooks/{notebookId}")
    fun deleteNotebookById(@Path("notebookId") notebookId: String): Call<Void>

    @GET("notebooks/{notebookId}/notes")
    fun getNotesFromNotebook(@Path("notebookId") notebookId: String, @Query("archived") archived: Boolean = false): Call<List<NoteDto>>

    @GET("notebooks/{notebookId}/notes/{noteId}")
    fun getNoteFromNotebookById(@Path("notebookId") notebookId: String, @Path("noteId") noteId: String): Call<NoteDto>

    @POST("notebooks/{notebookId}/notes")
    fun createNoteInNotebook(@Path("notebookId") notebookId: String, @Body noteDto: NoteDto): Call<NoteDto>

    @PUT("notebooks/{notebookId}/notes/{noteId}")
    fun updateNoteInNotebook(@Path("notebookId") notebookId: String, @Path("noteId") noteId: String, @Body noteDto: NoteDto): Call<NoteDto>

    @POST("notebooks/{notebookId}/notes/{noteId}/archive")
    fun archiveNoteInNotebook(@Path("notebookId") notebookId: String, @Path("noteId") noteId: String): Call<Void>

    @POST("notebooks/{notebookId}/notes/{noteId}/restore")
    fun restoreNoteInNotebook(@Path("notebookId") notebookId: String, @Path("noteId") noteId: String): Call<Void>

    @DELETE("notebooks/{notebookId}/notes/{noteId}")
    fun deleteNoteFromNotebookById(@Path("notebookId") notebookId: String, @Path("noteId") noteId: String): Call<Void>
}

data class NotebookDto(var id: String = "", var name: String, var description: String, var notes: MutableList<NoteDto> = ArrayList())

data class NoteDto(var id: String = "", var name: String, var content: String, var archived: Boolean = false)
