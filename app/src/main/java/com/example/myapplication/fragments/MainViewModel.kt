package com.example.myapplication.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.*
import com.example.myapplication.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currentProgress = MutableLiveData<CurrentProgress>()

    private var programWithExerciseById: ProgramHasExercise? = null

    private val _exerciseForToday = MutableLiveData(emptyList<ExerciseHasLoad>())

    private val _trainingDays = MutableLiveData<TrainingDays>()


    fun getProgramWithExercisesById(id: Int): ProgramHasExercise? {
        viewModelScope.launch {
            programWithExerciseById = repository.getProgramWithExercisesById(id)
        }
        return programWithExerciseById
    }

//    fun getExerciseForToday(
//        bottom_id: Int,
//        top_id: Int,
//        currentDay: Int,
//        currentWeek: Int
//    ): StateFlow<List<ExerciseHasLoad>> {
//        viewModelScope.launch {
//            _exerciseForToday.value =
//                repository.getExercisesHasLoadForToday(
//                    bottom_id,
//                    top_id,
//                    currentDay,
//                    currentWeek
//                )
//        }
//        return _exerciseForToday
//    }
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