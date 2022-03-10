package com.brxq.gyminstructor.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.ExerciseHasLoad
import com.brxq.gyminstructor.room.ProgramExerciseDatabase
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val database: ProgramExerciseDatabase
) {

    val allExercise = database.exerciseDao().getAll()

    private var today : LiveData<List<ExerciseHasLoad>>? = null

    fun getTodayExercise(id : Int, day : Int, week : Int) : LiveData<List<ExerciseHasLoad>>{
        today = database.exerciseDao().getToday(id, day, week)
        return today as LiveData<List<ExerciseHasLoad>>
    }

    private var progress : LiveData<CurrentProgress>? = null

    fun getCurrentProgress(): LiveData<CurrentProgress>? {
        progress = database.exerciseDao().getCurrentProgress()
        return progress
    }

}