package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Program(
    @PrimaryKey val program_id : Int,
    val program_title : String,
    val speciality: String,
    val days : Int,
    val weeks : Int,
    val difficulty : Int?,
    val bodyweight : Int?
)