package com.example.myapplication.ui.exercise.calendar

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.TrainingDays

class CalendarAdapter(
    private val onDateClick: ViewHolder.OnDateClick,
    private val trainingDays: TrainingDays
) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var currentDayIndex = 0
    private var listTrainingDays = ArrayList<Int>()
    private var listOfDays = listOf(7, 8, 9, 10, 11, 12, 13)

    //Календарь начинается в воскресенья, сделать возможность установки календаря с понедельника
    private val listOfWeekdays = listOf("Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat")

    private val trainingDay: TrainingDay =
        TrainingDay(Color.rgb(41, 46, 74), Color.rgb(70, 81, 194))
    private val currentDay: CurrentDay =
        CurrentDay(Color.rgb(70, 81, 194), Color.rgb(255, 255, 255))
    private val otherDay: OtherDay = OtherDay(Color.rgb(42, 42, 42), Color.argb(40, 255, 255, 255))

    data class TrainingDay(
        val background: Int,
        val text: Int
    )

    data class CurrentDay(
        val background: Int,
        val text: Int
    )

    data class OtherDay(
        val background: Int,
        val text: Int
    )

    class ViewHolder(
        itemView: View,
        private val onDateClick: OnDateClick
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var day: TextView = itemView.findViewById(R.id.calendarDayTV)
        var week: TextView = itemView.findViewById(R.id.calendarWeekdayTV)

        init {
            itemView.setOnClickListener(this)
        }

        interface OnDateClick {
            fun onDateClick(pos: Int)
        }

        override fun onClick(v: View?) {
            onDateClick.onDateClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        getTrainingDays()
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.calendar, parent, false),
            onDateClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("CURRENTDAYINDEX", currentDayIndex.toString())
        if (listTrainingDays.contains(position)) {
            holder.day.background.setTint(trainingDay.background)
            holder.day.setTextColor(trainingDay.text)
        } else {
            holder.day.background.setTint(otherDay.background)
            holder.day.setTextColor(otherDay.text)
        }
        if (position == currentDayIndex) {
            holder.day.background.setTint(currentDay.background)
            holder.day.setTextColor(currentDay.text)
        }
        holder.day.text = listOfDays[position].toString()
        holder.week.text = listOfWeekdays[position]
    }

    override fun getItemCount(): Int {
        return listOfDays.size
    }
    //Заменить проверку уставновленных тренировочных дней на более адекватный вариант??
    private fun getTrainingDays() {
        listTrainingDays.clear()
        if (trainingDays.day0 == 1) {
            listTrainingDays.add(0)
        }
        if (trainingDays.day1 == 1) {
            listTrainingDays.add(1)
        }
        if (trainingDays.day2 == 1) {
            listTrainingDays.add(2)
        }
        if (trainingDays.day3 == 1) {
            listTrainingDays.add(3)
        }
        if (trainingDays.day4 == 1) {
            listTrainingDays.add(4)
        }
        if (trainingDays.day5 == 1) {
            listTrainingDays.add(5)
        }
        if (trainingDays.day6 == 1) {
            listTrainingDays.add(6)
        }
    }

    fun setData(pos: Int) {
        currentDayIndex = pos
        notifyDataSetChanged()
    }
}