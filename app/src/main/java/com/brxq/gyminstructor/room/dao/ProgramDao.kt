package com.brxq.gyminstructor.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.Program
import com.brxq.gyminstructor.model.ProgramHasExercise
import com.brxq.gyminstructor.model.TrainingDays

@Dao
interface ProgramDao {

    @Query("SELECT * FROM PROGRAM")
    fun getAllPrograms() : LiveData<List<Program>>

    @Query("SELECT * FROM Program WHERE program_id = :id")
    suspend fun getProgramById(id : Int) : Program

    @Query("SELECT * FROM Program")
    suspend fun getAllExercisesAndPrograms() : List<ProgramHasExercise>

    @Query("SELECT * FROM Program WHERE program_id = :id")
    suspend fun getProgramWithExercisesById(id : Int) : ProgramHasExercise

    @Query("SELECT * FROM TrainingDays")
    suspend fun getTrainingDays() : TrainingDays

}