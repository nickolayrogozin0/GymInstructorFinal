package com.brxq.gyminstructor.ui.exercise

import android.util.Log
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
        viewModelScope.launch {
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

    fun changeWeek(i: Int) : LiveData<CurrentProgress>{
        if (i < 0){

            if (currentProgress.value!!.week - i >= 0){
                currentProgress.value!!.week--
            }

        }else {

            if (currentProgress.value!!.week + i < currentProgram.value!!.weeks){
                currentProgress.value!!.week++
            }

        }

        updateCurrentProgress(currentProgress.value!!)

        Log.i("CURRENT_WEEK", currentProgress.value!!.week.toString())

        return currentProgress

    }


}