package com.brxq.gyminstructor.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.brxq.gyminstructor.model.*

@Dao
interface ExerciseDao{

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getAllExercisesAndLoads() : List<ExerciseHasLoad>

    @Query("SELECT * FROM Exercise WHERE (exercise_id BETWEEN :bottom_id AND :top_id) AND (day = :currentDay) AND (week = :currentWeek)")
    fun getExercisesHasLoadForToday(bottom_id : Int, top_id : Int, currentDay : Int, currentWeek : Int) : List<ExerciseHasLoad>

    @Update
    fun finishExercise(exercise: Exercise)

}