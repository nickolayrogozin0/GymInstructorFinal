package com.brxq.gyminstructor.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.ProgramHasExercise
import com.brxq.gyminstructor.model.TrainingDays

@Dao
interface ProgramDao {

    @Transaction
    @Query("SELECT * FROM Program")
    fun getAllExercisesAndPrograms() : List<ProgramHasExercise>

    @Query("SELECT * FROM Program WHERE program_id = :id")
    fun getProgramWithExercisesById(id : Int) : ProgramHasExercise

    @Query("SELECT * FROM CurrentProgress")
    fun getCurrentProgress() : CurrentProgress

    @Query("SELECT * FROM TrainingDays")
    fun getTrainingDays() : TrainingDays

}