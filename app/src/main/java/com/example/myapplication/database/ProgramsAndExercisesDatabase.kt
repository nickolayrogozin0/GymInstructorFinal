package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.ExerciseDao
import com.example.myapplication.database.dao.ProgramDao
import com.example.myapplication.model.*

@Database(
    entities = [
        Program::class,
        Exercise::class,
        Load::class,
        CurrentProgress::class,
        TrainingDays::class
               ],
    exportSchema = false,
    version = 6
)
abstract class ProgramsAndExercisesDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun programDao() : ProgramDao
}
