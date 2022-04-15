package com.brxq.gyminstructor.repository

import androidx.lifecycle.LiveData
import com.brxq.gyminstructor.model.Note
import com.brxq.gyminstructor.room.NoteDatabase
import com.brxq.gyminstructor.room.ProgramExerciseDatabase
import com.brxq.gyminstructor.room.dao.NotesDao
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: NoteDatabase
) {

    // Получение заметок
    val allNotes: LiveData<List<Note>> = database.notesDao().getAllNotes()

    // Подстановка значений
    suspend fun insert(note: Note){
        database.notesDao().insert(note)
    }

    suspend fun delete(note: Note){
        database.notesDao().delete(note)
    }

    suspend fun update(note: Note){
        database.notesDao().update(note)
    }

}