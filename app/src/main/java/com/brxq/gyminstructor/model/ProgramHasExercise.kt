package com.brxq.gyminstructor.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProgramHasExercise(
    @Embedded
    val program : Program,
    @Relation(
        parentColumn = "program_id",
        entityColumn = "program_id"
    )
    val listOfExercises : List<Exercise>
)
