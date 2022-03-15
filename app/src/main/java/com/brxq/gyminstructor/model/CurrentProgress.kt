package com.brxq.gyminstructor.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentProgress(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val program_id: Int,
    var day : Int,
    var week : Int
)
