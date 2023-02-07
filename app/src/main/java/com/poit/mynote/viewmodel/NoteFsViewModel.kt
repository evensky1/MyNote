package com.poit.mynote.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.poit.mynote.entity.Note
import com.poit.mynote.repository.NoteFsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class NoteFsViewModel(application: Application) : NoteViewModel(application) {
    override val allNotes: LiveData<List<Note>>
    private val noteRepository: NoteFsRepository

    init {
        val storage = File("${application.applicationContext.filesDir}/myNoteStore.txt")
        noteRepository = NoteFsRepository(storage)
        allNotes = noteRepository.allNotes
    }

    override fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNote(note)
    }

    override fun updateNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        noteRepository.updateNote(note)
    }

    override fun createNote(note: Note) = viewModelScope.launch (Dispatchers.IO) {
        noteRepository.createNote(note)
    }
}