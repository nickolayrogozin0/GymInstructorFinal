package com.brxq.gyminstructor.ui.programs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val programsViaQuiz = MutableLiveData<List<Program>>()

    fun updateSelectedProgram(currentProgress: CurrentProgress) {
        viewModelScope.launch {
            repository.updateSelectedProgram(currentProgress)
        }
    }

    fun getProgramsViaQuiz(
        inputSpeciality: String,
        inputDays: Int
    ): LiveData<List<Program>> {
        viewModelScope.launch {
            programsViaQuiz.value = repository.getProgramsViaQuiz(inputSpeciality, inputDays)
        }
        return programsViaQuiz
    }

}