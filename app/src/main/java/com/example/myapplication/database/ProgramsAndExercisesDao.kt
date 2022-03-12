package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ProgramsAndExercisesDao{

    @Query("SELECT * FROM Program")
    fun getAllExercisesAndPrograms() : List<ProgramHasExercise>


    @Query("SELECT * FROM Exercise")
    fun getAllExercisesAndLoads() : List<ExerciseHasLoad>

    @Query("SELECT * FROM CurrentProgress")
    suspend fun getCurrentProgress() : CurrentProgress

    @Update
    suspend fun updateCurrentProgress(currentProgress: CurrentProgress)

    @Query("SELECT * FROM TrainingDays")
    suspend fun getTrainingDays() : TrainingDays

    @Query("SELECT * FROM Program WHERE program_id = :id")
    suspend fun getProgramWithExercisesById(id : Int) : ProgramHasExercise

//    @Query("SELECT * FROM Exercise WHERE (exercise_id BETWEEN :bottom_id AND :top_id) AND (day = :currentDay) AND (week = :currentWeek)")
//    suspend fun getExercisesHasLoadForToday(bottom_id : Int, top_id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>

    @Query("SELECT * FROM Exercise WHERE (program_id = :id) AND (day = :currentDay) AND (week = :currentWeek)")
    suspend fun getExercisesHasLoadForToday(id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>

    @Update
    suspend fun finishExercise(exercise: Exercise)


}