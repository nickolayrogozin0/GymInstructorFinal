package com.brxq.gyminstructor.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brxq.gyminstructor.model.Note
import com.brxq.gyminstructor.room.dao.NotesDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase() {

    abstract fun notesDao(): NotesDao

}