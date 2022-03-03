package com.example.myapplication

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

@Entity
data class Exercise(
    @PrimaryKey val exercise_id : Int,
    val program_id: Int,
    val exercise_title : String,
    val day : Int,
    val week : Int,
    var isComplete : Int,
)

@Entity
data class Load(
    @PrimaryKey val load_id : Int,
    val exercise_id: Int,
    val reps : Int,
    val sets : Int,
    val intensity : Int,
    val rpe : Int?
)

data class ProgramHasExercise(
    @Embedded
    val program : Program,
    @Relation(
        parentColumn = "program_id",
        entityColumn = "program_id"
    )
    val listOfExercises : List<Exercise>
)

data class ExerciseHasLoad(
    @Embedded
    val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id"
    )
    val listOfLoads : List<Load>
)
@Entity
data class CurrentProgress(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val program_id: Int,
    var day : Int,
    var week : Int
)

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