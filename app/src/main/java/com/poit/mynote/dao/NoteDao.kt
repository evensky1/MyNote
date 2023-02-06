package com.poit.mynote.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.poit.mynote.entity.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id")
    fun getAllNotes(): LiveData<List<Note>>
}