package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingDays(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val day0 : Int,
    val day1 : Int,
    val day2 : Int,
    val day3 : Int,
    val day4 : Int,
    val day5 : Int,
    val day6 : Int
)