package com.brxq.gyminstructor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Load(
    @PrimaryKey val load_id : Int,
    val exercise_id: Int,
    val reps : Int,
    val sets : Int,
    val intensity : Int,
    val rpe : Int?
)
