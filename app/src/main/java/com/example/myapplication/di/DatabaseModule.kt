package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.ProgramsAndExercisesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : ProgramsAndExercisesDatabase{
        return Room.databaseBuilder(
            context,
            ProgramsAndExercisesDatabase::class.java,
            "database-name",
        )
            .createFromAsset("database/mydb.db")
            .build()
    }

}