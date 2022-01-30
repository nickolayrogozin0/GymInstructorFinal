package com.example.myapplication.database

import android.content.Context
import androidx.room.*
import com.example.myapplication.*

@Database(entities = [Program::class, Exercise::class, Load::class, CurrentProgress::class, TrainingDays::class], exportSchema = false, version = 5)
abstract class ProgramsAndExercisesDatabase : RoomDatabase() {
    abstract fun programDao(): ProgramsAndExercisesDao

    companion object {
        @Volatile
        private var INSTANCE: ProgramsAndExercisesDatabase? = null

        fun getDatabase(context: Context): ProgramsAndExercisesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProgramsAndExercisesDatabase::class.java,
                    "database-name",
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("database/mydb.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

}
