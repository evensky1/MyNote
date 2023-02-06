package com.poit.mynote.repository

import androidx.lifecycle.MutableLiveData
import com.poit.mynote.dao.NoteDao
import com.poit.mynote.entity.Note

class NoteRepository (private val noteDao: NoteDao) {

    val allNotes: MutableLiveData<List<Note>>

    init {
        val notes = noteDao.getAllNotes().value
        println("from repo with $noteDao ::::: $notes")
        allNotes = MutableLiveData(notes)
    }

    fun insert(note: Note) {
        noteDao.insert(note)
    }

    fun delete(note: Note) {
        noteDao.delete(note)
    }

    fun update(note: Note) {
        noteDao.update(note)
    }
}