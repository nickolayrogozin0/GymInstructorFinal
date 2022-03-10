package com.brxq.gyminstructor.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseHasLoad(
    @Embedded
    val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id"
    )
    val listOfLoads : List<Load>
)
