package com.example.introductorylab.infrastructure

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.introductorylab.API_ROOT
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook
import com.example.introductorylab.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//@RequiresApi(Build.VERSION_CODES.N)
//object NotebookRepository : Repository<Notebook> {
//
//    private val gateway: NotebookGateway? = buildGateway()
//
//    private fun buildGateway(): NotebookGateway? {
//        val retrofit = Retrofit.Builder()
//                .baseUrl(API_ROOT)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//        return retrofit.create(NotebookGateway::class.java)
//    }
//
//    init {
//
//
//        runBlocking {
//            bootstrapData()
//        }
//    }
//
//    private suspend fun bootstrapData() {
//        for (i in 1..5) {
//            val notebook = Notebook(
//                    i.toString(),
//                    "Notebook $i",
//                    "Description for notebook $i"
//            )
//            for (j in 1..30) {
//                var note = Note("", "Note $j", "Content of the note $j")
//                note = NoteRepository.save(note)
//                notebook.addNote(note)
//            }
//            save(notebook)
//        }
//    }
//
//    override suspend fun findById(id: String): Notebook? {
//        return
//    }
//
//    override suspend fun findByOrder(order: Int): Notebook? {
//        val allItems = findAll()
//        return if (order < allItems.size) allItems[order] else null
//    }
//
//    override suspend fun findAll(): List<Notebook> = withContext(Dispatchers.IO) {
//        val response = gateway?.getAllNotebooks()?.execute()
//        val dtoList = response?.body() ?: emptyList()
//
//        return@withContext dtoList.map { mapNotebook(it) }
//    }
//
//    override suspend fun countAll(): Int {
//        return findAll().size
//    }
//
//    override suspend fun save(obj: Notebook): Notebook {
//        return Notebook("", "test", "test")
//    }
//
//    override suspend fun deleteById(id: String) {
//    }
//
//    private fun mapNotebook(dto: NotebookDto): Notebook {
//        return Notebook(dto.id, dto.name, dto.description)
//    }
//}