package com.example.myapplication.screens.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.*
import com.example.myapplication.model.*
import com.example.myapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currentProgress = MutableLiveData<CurrentProgress>()

    private val _programWithExerciseById = MutableLiveData<ProgramHasExercise>()

    private val _exerciseForToday = MutableLiveData(emptyList<ExerciseHasLoad>())

    private val _trainingDays = MutableLiveData<TrainingDays>()


    fun getProgramWithExercisesById(id: Int): LiveData<ProgramHasExercise> {
        viewModelScope.launch {
            _programWithExerciseById.value = repository.getProgramWithExercisesById(id)
        }
        return _programWithExerciseById
    }


    fun getExerciseForToday(
        program_id : Int,
        currentDay: Int,
        currentWeek: Int
    ): LiveData<List<ExerciseHasLoad>> {
        viewModelScope.launch {
            _exerciseForToday.value =
                repository.getExercisesHasLoadForToday(
                    program_id,
                    currentDay,
                    currentWeek
                )
        }
        return _exerciseForToday
    }

    fun getTrainingDays(): LiveData<TrainingDays> {
        viewModelScope.launch {
            _trainingDays.value = repository.getTrainingDays()
            Log.i("TRAINING_DAYS_VIEWMODEL", _trainingDays.value.toString())
        }
        return _trainingDays
    }

    fun getCurrentProgress(): LiveData<CurrentProgress> {
        viewModelScope.launch {
            _currentProgress.value = repository.getCurrentProgress()
            Log.i("CURRENT_PROGRESS_VM", _currentProgress.value.toString())
        }
        return _currentProgress
    }

    fun updateCurrentProgress(currentProgress: CurrentProgress) {
        viewModelScope.launch {
            repository.updateCurrentProgress(currentProgress)
        }
    }


    fun finishExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.finishExercise(exercise)
        }
    }


}