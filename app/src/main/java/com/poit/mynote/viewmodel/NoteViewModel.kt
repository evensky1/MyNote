package com.poit.mynote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.poit.mynote.dao.db.NoteDatabase
import com.poit.mynote.entity.Note
import com.poit.mynote.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class NoteViewModel(application: Application) : AndroidViewModel(application) {
    open val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    open fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    open fun updateNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        repository.update(note)
    }

    open fun createNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(note)
    }
}