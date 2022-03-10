package com.brxq.gyminstructor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.brxq.gyminstructor.model.*

@Dao
interface ExerciseDao{

    @Transaction
    @Query("SELECT * FROM Exercise")
    fun getAll() : LiveData<List<ExerciseHasLoad>>

    @Transaction
    @Query("SELECT * FROM Exercise WHERE program_id = :id AND day = :day AND week = :week AND isComplete = 0")
    fun getToday(id : Int, day : Int, week : Int) : LiveData<List<ExerciseHasLoad>>

    @Query("SELECT * FROM CurrentProgress")
    fun getCurrentProgress() : LiveData<CurrentProgress>
}