package com.example.introductorylab.infrastructure

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.introductorylab.domain.Note
import com.example.introductorylab.domain.Notebook

@RequiresApi(Build.VERSION_CODES.N)
object NotebookRepository : InMemoryRepository<Notebook>() {

    init {
        for (i in 1..5) {
            val notebook = Notebook(
                    i.toString(),
                    "Notebook $i",
                    "Description for notebook $i"
                )
            for (j in 1..30) {
                var note = Note("", "Note $j", "Content of the note $j")
                note = NoteRepository.save(note)
                notebook.addNote(note)
            }
            save(notebook)
        }
    }
}