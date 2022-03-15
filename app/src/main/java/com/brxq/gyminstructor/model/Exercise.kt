package com.brxq.gyminstructor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey val exercise_id : Int,
    val program_id: Int,
    val exercise_title : String,
    val day : Int,
    val week : Int,
    var isComplete : Int,
)
