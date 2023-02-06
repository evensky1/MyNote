package com.poit.mynote.repository

import androidx.lifecycle.LiveData
import com.poit.mynote.dao.NoteDao
import com.poit.mynote.entity.Note

class NoteRepository (private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

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