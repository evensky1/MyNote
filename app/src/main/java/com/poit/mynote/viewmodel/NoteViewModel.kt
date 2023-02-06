package com.poit.mynote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.poit.mynote.dao.db.NoteDatabase
import com.poit.mynote.entity.Note
import com.poit.mynote.repository.NoteRepository
import com.poit.mynote.repository.NoteRepositoryFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes: MutableLiveData<List<Note>>
    private var repository: NoteRepository
    private val repositoryFactory = NoteRepositoryFactory()

    init {
        repository = repositoryFactory.getRepositoryInstance("FS", application)
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

    fun switchNoteSource(dbType: String, application: Application) {
        repository = repositoryFactory.getRepositoryInstance(dbType, application)
        allNotes.value = repository.allNotes.value
    }
}