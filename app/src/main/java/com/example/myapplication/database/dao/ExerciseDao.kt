package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplication.model.Exercise
import com.example.myapplication.model.ExerciseHasLoad

@Dao
interface ExerciseDao {


    @Query("SELECT * FROM Exercise")
    fun getAllExercisesAndLoads(): List<ExerciseHasLoad>


    @Query("SELECT * FROM Exercise WHERE (program_id = :id) AND (day = :currentDay) AND (week = :currentWeek)")
    suspend fun getExercisesHasLoadForToday(
        id: Int,
        currentDay: Int,
        currentWeek: Int
    ): List<ExerciseHasLoad>

    @Update
    suspend fun finishExercise(exercise: Exercise)


}