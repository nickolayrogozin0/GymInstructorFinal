package com.brxq.gyminstructor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentProgress(
    @PrimaryKey(autoGenerate = true) val id : Int,
    var program_id: Int,
    var day : Int,
    var week : Int,
    var max_squat : Float,
    var max_bench : Float,
    var max_deadlift : Float
)
