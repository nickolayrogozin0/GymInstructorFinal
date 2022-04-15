package com.brxq.gyminstructor.ui.programs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.Program
import com.brxq.gyminstructor.repository.ProgramRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val repository: ProgramRepository
) : ViewModel() {

    val allPrograms = repository.allPrograms

    fun updateSelectedProgram(currentProgress : CurrentProgress){

        viewModelScope.launch {
            repository.updateSelectedProgram(currentProgress)
        }

    }

}