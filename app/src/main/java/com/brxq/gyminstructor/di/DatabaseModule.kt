package com.brxq.gyminstructor.di

import android.content.Context
import androidx.room.Room
import com.brxq.gyminstructor.room.NoteDatabase
import com.brxq.gyminstructor.room.ProgramExerciseDatabase
import com.brxq.gyminstructor.ui.programs.ProgramViewModel
import com.brxq.gyminstructor.util.Constants.EXERCISE_DATABASE
import com.brxq.gyminstructor.util.Constants.NOTE_DATABASE
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
        @ApplicationContext context: Context
    ): ProgramExerciseDatabase {
        return Room.databaseBuilder(
            context,
            ProgramExerciseDatabase::class.java,
            EXERCISE_DATABASE
        )
            .fallbackToDestructiveMigration()
            .createFromAsset("database/mydb.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NOTE_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }



}