package com.brxq.gyminstructor.room

import androidx.room.*
import com.brxq.gyminstructor.model.*
import com.brxq.gyminstructor.room.dao.ExerciseDao
import com.brxq.gyminstructor.room.dao.ProgramDao

@Database(
    entities = [
        Program::class,
        Exercise::class,
        Load::class,
        CurrentProgress::class,
        TrainingDays::class
               ],
    exportSchema = false,
    version = 12
)
abstract class ProgramExerciseDatabase : RoomDatabase() {

    abstract fun programDao(): ProgramDao
    abstract fun exerciseDao() : ExerciseDao

}
