package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.model.CurrentProgress
import com.example.myapplication.model.ProgramHasExercise
import com.example.myapplication.model.TrainingDays

@Dao
interface ProgramDao {

    @Query("SELECT * FROM Program")
    fun getAllExercisesAndPrograms() : List<ProgramHasExercise>

    @Query("SELECT * FROM CurrentProgress")
    suspend fun getCurrentProgress() : CurrentProgress

    @Query("SELECT * FROM TrainingDays")
    suspend fun getTrainingDays() : TrainingDays

    @Update
    suspend fun updateCurrentProgress(currentProgress: CurrentProgress)

    @Query("SELECT * FROM Program WHERE program_id = :id")
    suspend fun getProgramWithExercisesById(id : Int) : ProgramHasExercise
}