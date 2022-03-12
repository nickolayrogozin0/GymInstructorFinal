package com.example.myapplication.repository

import com.example.myapplication.database.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val database: ProgramsAndExercisesDatabase
) {

    suspend fun getProgramWithExercisesById(id : Int) : ProgramHasExercise {
        return database.programDao().getProgramWithExercisesById(id)
    }

    suspend fun getCurrentProgress() : CurrentProgress {
        return database.programDao().getCurrentProgress()
    }

    suspend fun updateCurrentProgress(currentProgress: CurrentProgress){
        database.programDao().updateCurrentProgress(currentProgress)
    }


//    suspend fun getExercisesHasLoadForToday(bottom_id : Int, top_id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>{
//        return database.programDao().getExercisesHasLoadForToday(bottom_id, top_id, currentDay, currentWeek)
//    }
    suspend fun getExercisesHasLoadForToday(program_id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>{
        return database.programDao().getExercisesHasLoadForToday(program_id, currentDay, currentWeek)
    }

    suspend fun getTrainingDays() : TrainingDays {
        return database.programDao().getTrainingDays()
    }

    suspend fun finishExercise(exercise: Exercise){
        database.programDao().finishExercise(exercise)
    }

}