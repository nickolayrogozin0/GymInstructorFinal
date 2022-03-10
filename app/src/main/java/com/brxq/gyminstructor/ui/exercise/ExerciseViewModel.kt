package com.brxq.gyminstructor.ui.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.ExerciseHasLoad
import com.brxq.gyminstructor.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    val allExercise = repository.allExercise

    private var today : LiveData<List<ExerciseHasLoad>>? = null

    fun getTodayExercise(id : Int, day : Int, week : Int) : LiveData<List<ExerciseHasLoad>>? {
        viewModelScope.launch {
           today = repository.getTodayExercise(id, day, week)
        }
       return today
    }

    private var progress : LiveData<CurrentProgress>? = null

    fun getCurrentProgress() : LiveData<CurrentProgress>? {
        viewModelScope.launch {
            progress = repository.getCurrentProgress()
        }
        return progress
    }

}