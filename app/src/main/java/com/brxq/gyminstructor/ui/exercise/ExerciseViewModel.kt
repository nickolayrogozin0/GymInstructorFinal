package com.brxq.gyminstructor.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.Exercise
import com.brxq.gyminstructor.model.ExerciseHasLoad
import com.brxq.gyminstructor.model.Program
import com.brxq.gyminstructor.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    val allProgram = repository.getAllPrograms()
    val allExercise = repository.allExercise

    private val currentProgram = MutableLiveData<Program>()

    private val todayExercise = MutableLiveData<List<ExerciseHasLoad>>()

    fun getTodayExercise(id : Int, day : Int, week : Int) : LiveData<List<ExerciseHasLoad>> {
        viewModelScope.launch {
           todayExercise.value = repository.getTodayExercise(id, day, week)
        }
       return todayExercise
    }

    private val currentProgress = MutableLiveData<CurrentProgress>()

    fun getCurrentProgress() : LiveData<CurrentProgress> {
        viewModelScope.launch {
            currentProgress.value = repository.getCurrentProgress()
        }
        return currentProgress
    }

    fun updateCurrentProgress(currentProgress: CurrentProgress){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentProgress(currentProgress)
        }
    }

    fun finishExercise(exercise: Exercise){
        viewModelScope.launch {
            repository.finishExercise(exercise)
        }
    }

    fun getProgramById(id : Int) : LiveData<Program>{
        viewModelScope.launch {
            currentProgram.value = repository.getProgramById(id)
        }
        return currentProgram
    }



}