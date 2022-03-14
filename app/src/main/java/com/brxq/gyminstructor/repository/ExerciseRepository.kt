package com.brxq.gyminstructor.repository

import androidx.lifecycle.LiveData
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.ExerciseHasLoad
import com.brxq.gyminstructor.room.ProgramExerciseDatabase
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val database: ProgramExerciseDatabase
) {

    val allExercise = database.exerciseDao().getAll()

    private var todayExercise : List<ExerciseHasLoad>? = null

    suspend fun getTodayExercise(id : Int, day : Int, week : Int) : List<ExerciseHasLoad>?{
        todayExercise = database.exerciseDao().getToday(id, day, week)
        return todayExercise
    }

//    private var currentProgress : LiveData<CurrentProgress>? = null
//
//    fun getCurrentProgress(): LiveData<CurrentProgress>? {
//        currentProgress = database.exerciseDao().getCurrentProgress()
//        return currentProgress
//    }

    private var currentProgress : CurrentProgress? = null

    suspend fun getCurrentProgress(): CurrentProgress? {
        currentProgress = database.exerciseDao().getCurrentProgress()
        return currentProgress
    }

    suspend fun updateCurrentProgress(currentProgress: CurrentProgress) {
        database.exerciseDao().updateCurrentProgress(currentProgress)
    }

}