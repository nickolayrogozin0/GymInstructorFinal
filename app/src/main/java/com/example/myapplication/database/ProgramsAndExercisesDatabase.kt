package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

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

    abstract fun programDao(): ProgramsAndExercisesDao

}
