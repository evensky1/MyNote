package com.poit.mynote.repository

import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.poit.mynote.entity.Note
import java.io.File
import java.nio.charset.Charset

class NoteFsRepository(val storage: File) {
    val allNotes = MutableLiveData(storage.readLines().map { Klaxon().parse<Note>(it)!! })

    fun deleteNote(note: Note) {
        val str = storage.readText()
            .replace("\\{.+\"id\" : ${note.id}.+\\}\n".toRegex(), "")

        storage.writeText(str);

        allNotes.postValue(storage.readLines().map { Klaxon().parse<Note>(it)!! })
    }

    fun updateNote(note: Note) {
        val str = storage.readText()
            .replace("\\{.+\"id\" : ${note.id}.+\\}".toRegex(), Klaxon().toJsonString(note))

        storage.writeText(str);

        allNotes.postValue(storage.readLines().map { Klaxon().parse<Note>(it)!! })
    }

    fun createNote(note: Note) {
        note.id = allNotes.value?.size?.plus(1) ?: 0
        storage.appendText(Klaxon().toJsonString(note) + '\n', Charset.defaultCharset())
        allNotes.postValue(storage.readLines().map { Klaxon().parse<Note>(it)!! })
    }
}