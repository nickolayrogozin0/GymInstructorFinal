package com.brxq.gyminstructor.repository

import androidx.lifecycle.LiveData
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.model.Program
import com.brxq.gyminstructor.room.ProgramExerciseDatabase
import javax.inject.Inject

class ProgramRepository @Inject constructor(
    private val database: ProgramExerciseDatabase
) {

    val allPrograms = database.programDao().getAllPrograms()

    suspend fun updateSelectedProgram(currentProgress: CurrentProgress) {
        database.programDao().updateSelectedProgram(currentProgress)
    }

    suspend fun getProgramsViaQuiz(
        inputSpeciality: String,
        inputDays: Int
    ): List<Program> {
        return database.programDao().getProgramsViaQuiz(inputSpeciality, inputDays)
    }

}