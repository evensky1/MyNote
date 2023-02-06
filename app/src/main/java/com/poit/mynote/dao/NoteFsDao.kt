package com.poit.mynote.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Klaxon
import com.poit.mynote.entity.Note
import java.io.File
import java.nio.charset.Charset

class NoteFsDao : NoteDao {

    private val storage: File = File("/data/user/0/com.poit.mynote/files/myNoteStore.txt")

    override fun insert(note: Note) {
        storage.appendText(Klaxon().toJsonString(note) + '\n', Charset.defaultCharset())
    }

    override fun update(note: Note) {
        val str = storage.readText()
            .replace("\\{.+\"id\" : ${note.id}.+\\}".toRegex(), Klaxon().toJsonString(note))

        storage.writeText(str);
    }

    override fun delete(note: Note) {
        val str = storage.readText()
            .replace("\\{.+\"id\" : ${note.id}.+\\}\n".toRegex(), "")

        storage.writeText(str);
    }

    override fun getAllNotes(): LiveData<List<Note>> {
        return MutableLiveData(storage.readLines().map { Klaxon().parse<Note>(it)!! })
    }
}