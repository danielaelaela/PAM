package com.example.introductorylab.util

import android.content.Context
import android.content.Intent
import com.example.introductorylab.NOTEBOOK_ID
import com.example.introductorylab.NOTE_ID
import com.example.introductorylab.activity.EditNote
import com.example.introductorylab.activity.EditNotebook
import com.example.introductorylab.activity.Notebook

fun cutString(value: String, length: Int = 100): String =
    if (value.length < length)
        value
    else
        (value.substring(0, length - 3) + "...")

fun openNotebook(context: Context, notebookId: String) {
    val intent = Intent(context, Notebook::class.java)
    intent.putExtra(NOTEBOOK_ID, notebookId)
    context.startActivity(intent)
}

fun openEditNotebook(context: Context, notebookId: String = "") {
    val intent = Intent(context, EditNotebook::class.java)
    intent.putExtra(NOTEBOOK_ID, notebookId)
    context.startActivity(intent)
}

fun openEditNote(context: Context, notebookId: String, noteId: String = "") {
    val intent = Intent(context, EditNote::class.java)
    intent.putExtra(NOTE_ID, noteId)
    intent.putExtra(NOTEBOOK_ID, notebookId)
    context.startActivity(intent)
}