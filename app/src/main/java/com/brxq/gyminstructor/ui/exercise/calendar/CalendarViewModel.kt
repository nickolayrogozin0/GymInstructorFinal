package com.brxq.gyminstructor.ui.exercise.calendar

import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
    private val calendar = java.util.Calendar.getInstance()

    private val listOfMonthDays = mutableListOf<Int>()
//    private val currentYearWeek = mutableStateOf(calendar.value.weekYear)


    val listOfWeekdays = when (calendar.firstDayOfWeek) {
        1 -> listOf("Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat")
        2 -> listOf("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun")
        7 -> listOf("Sat", "Sun", "Mon", "Tue", "Wen", "Thu", "Fri")
        else -> emptyList()
    }

    var currentDay = 0
    var currentWeek = 1
    private var maxWeeks = 4

    fun getWeekValues(): List<Int> {
        repeat(calendar[java.util.Calendar.DAY_OF_WEEK] - calendar.firstDayOfWeek) {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, -1)
        }
        listOfMonthDays.clear()
        repeat(7) {
            listOfMonthDays.add(calendar.get(java.util.Calendar.DAY_OF_MONTH))
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -7)
        return listOfMonthDays
    }

    fun decreaseWeek(): Int {
        if (currentWeek == 1) return currentWeek
        currentWeek -= 1
        calendar.add(java.util.Calendar.DAY_OF_MONTH, -7)
        return currentWeek
    }

    fun increaseWeek(): Int {
        if (currentWeek == maxWeeks) return currentWeek
        currentWeek += 1
        calendar.add(java.util.Calendar.DAY_OF_MONTH, 7)
        return currentWeek
    }

}