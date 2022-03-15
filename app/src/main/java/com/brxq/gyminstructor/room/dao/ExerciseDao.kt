package com.brxq.gyminstructor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.brxq.gyminstructor.model.*

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    fun getAll(): LiveData<List<ExerciseHasLoad>>

    @Query("SELECT * FROM Exercise WHERE program_id = :id AND day = :day AND week = :week AND isComplete = 0")
    suspend fun getToday(id: Int, day: Int, week: Int): List<ExerciseHasLoad>

    @Query("SELECT * FROM CurrentProgress")
    suspend fun getCurrentProgress(): CurrentProgress

    @Update
    suspend fun updateCurrentProgress(currentProgress: CurrentProgress)

    @Update
    suspend fun finishExercise(exercise: Exercise)
}