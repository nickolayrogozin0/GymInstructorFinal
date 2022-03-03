package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplication.*

@Dao
interface ProgramsAndExercisesDao{
    @Transaction
    @Query("SELECT * FROM Program")
    fun getAllExercisesAndPrograms() : List<ProgramHasExercise>

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getAllExercisesAndLoads() : List<ExerciseHasLoad>

    @Query("SELECT * FROM CurrentProgress")
    fun getCurrentProgress() : CurrentProgress

    @Query("SELECT * FROM TrainingDays")
    fun getTrainingDays() : TrainingDays

    @Query("SELECT * FROM Program WHERE program_id = :id")
    fun getProgramWithExercisesById(id : Int) : ProgramHasExercise

    @Query("SELECT * FROM Exercise WHERE (exercise_id BETWEEN :bottom_id AND :top_id) AND (day = :currentDay) AND (week = :currentWeek)")
    fun getExercisesHasLoadForToday(bottom_id : Int, top_id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>

    @Update
    fun finishExercise(exercise: Exercise)


}