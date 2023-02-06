package com.poit.mynote.repository

import android.app.Application
import com.poit.mynote.dao.NoteFsDao
import com.poit.mynote.dao.db.NoteDatabase
import java.util.NoSuchElementException

class NoteRepositoryFactory {
    fun getRepositoryInstance(type: String, application: Application):NoteRepository {
        return when (type) {
            "DB" -> NoteRepository(NoteDatabase.getDatabase(application).getNotesDao())
            "FS" -> NoteRepository(NoteFsDao())
            else -> throw NoSuchElementException()
        }
    }
}