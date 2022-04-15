package com.brxq.gyminstructor.ui.user.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brxq.gyminstructor.model.Note
import com.brxq.gyminstructor.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

}