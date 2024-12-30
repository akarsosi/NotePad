package com.ezzy.notepad // Ensure this matches your package structure

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes") // Specifies the table name
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generated primary key
    val title: String, // Title of the note
    val content: String, // Content of the note
    val timestamp: Long // Timestamp for creation or modification
)