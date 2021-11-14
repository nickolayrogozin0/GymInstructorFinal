package com.example.myapplication.database

import android.content.Context
import androidx.room.*
import com.example.myapplication.*

@Database(entities = [Program::class, Exercise::class, Load::class, CurrentProgress::class, TrainingDays::class], exportSchema = false, version = 5)
abstract class MyDatabase : RoomDatabase() {
    abstract fun programDao(): ProgramDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
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
