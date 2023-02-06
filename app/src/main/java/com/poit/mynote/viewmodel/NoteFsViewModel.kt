package com.poit.mynote.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beust.klaxon.Klaxon
import com.poit.mynote.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset

class NoteFsViewModel(application: Application) : NoteViewModel(application) {
    val storage: File

    init {
        storage = File("${application.applicationContext.filesDir}/myNoteStore.txt")
    }

    override fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        val str = storage.readText()
                         .replace("\\{.+\"id\" : ${note.id}.+\\}\n".toRegex(), "")

        storage.writeText(str);
    }

    override fun updateNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        val str = storage.readText()
                         .replace("\\{.+\"id\" : ${note.id}.+\\}".toRegex(), Klaxon().toJsonString(note))

        storage.writeText(str);
    }

    override fun createNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        storage.appendText(Klaxon().toJsonString(note) + '\n', Charset.defaultCharset())
    }
}