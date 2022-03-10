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

    fun getTodayExercise() : LiveData<List<ExerciseHasLoad>>{
        today = database.exerciseDao().getToday(1,1,0)
        return today as LiveData<List<ExerciseHasLoad>>
    }

}