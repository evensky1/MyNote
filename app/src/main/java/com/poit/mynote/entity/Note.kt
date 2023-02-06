package com.poit.mynote.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "last_modified")
    val lastModified: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}