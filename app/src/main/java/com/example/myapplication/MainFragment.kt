package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.flexbox.*


class MainFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick {

    private var MAX_WEEKS = 0

    private lateinit var myView: View
    private lateinit var myDB: MyDatabase
    private lateinit var exerciseAdapter: Adapter
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var currentProgress: CurrentProgress
    private lateinit var listOfExerciseHasLoad: List<ExerciseHasLoad>
    private lateinit var listOfExIds: ArrayList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_main, container, false)

        myDB = Room.databaseBuilder(
            requireContext(),
            MyDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .createFromAsset("database/mydb.db")
            .build()

        val exerciseRecycler = myView.findViewById<RecyclerView>(R.id.recycler)
        exerciseAdapter = Adapter(requireContext())
        val calendarRecycler = myView.findViewById<RecyclerView>(R.id.calendar)
        calendarAdapter = CalendarAdapter(this, myDB.programDao().getTrainingDays())

        currentProgress = myDB.programDao().getCurrentProgress()
        val listOfPrograms = myDB.programDao().getAllExercisesAndPrograms()

        var listOfExercise = emptyList<Exercise>()

        listOfPrograms.forEach {
            if (it.program.program_id == currentProgress.program_id) {
                listOfExercise = it.listOfExercises
            }
        }

        listOfPrograms.forEach {
            if (it.program.program_id == currentProgress.program_id) {
                MAX_WEEKS = it.program.weeks
            }
        }

        listOfExIds = ArrayList()
        listOfExercise.forEach {
            listOfExIds.add(it.exercise_id)
        }
        listOfExerciseHasLoad = myDB.programDao().getAllExercisesAndLoads()

        val list = findTodayExercises()
        exerciseRecycler.adapter = exerciseAdapter
        exerciseRecycler.layoutManager = LinearLayoutManager(requireContext())
        exerciseAdapter.setData(list)

        changeWeek()
        changeBlock()
        changeProgressBarAndCompleted()

        calendarRecycler.adapter = calendarAdapter
        val calendarLayout = FlexboxLayoutManager(requireContext())
        calendarLayout.justifyContent = JustifyContent.CENTER
        calendarLayout.alignItems = AlignItems.CENTER
        calendarLayout.flexDirection = FlexDirection.ROW
        calendarLayout.flexWrap = FlexWrap.WRAP
        calendarRecycler.layoutManager = calendarLayout
        calendarAdapter.setData(currentProgress.day)

        val right = myView.findViewById<ImageButton>(R.id.right)
        right.setOnClickListener {
            if (currentProgress.week < MAX_WEEKS - 1)
                currentProgress.week++
            exerciseAdapter.setData(findTodayExercises())
            changeWeek()
            changeProgressBarAndCompleted()
            if (currentProgress.week + 1 % 4 == 0) {
                changeBlock()
            }
        }

        val left = myView.findViewById<ImageButton>(R.id.left)
        left.setOnClickListener {
            Log.i("WEEK", currentProgress.week.toString())
            if (currentProgress.week != 0) {
                currentProgress.week--
                exerciseAdapter.setData(findTodayExercises())
            }
            changeWeek()
            changeProgressBarAndCompleted()
            if (currentProgress.week + 1 % 4 == 0) {
                changeBlock()
            }
        }

        return myView
    }

    private fun findTodayExercises(): List<ExerciseHasLoad> {
        val listOfToday = ArrayList<ExerciseHasLoad>()
        listOfExerciseHasLoad.forEachIndexed { index, item ->
            Log.i(
                "EXERCISES",
                "Item day " + item.exercise.day + " Item week " + item.exercise.week + " CurrentProgress DW " + currentProgress.day + " " + currentProgress.week
            )
            if (listOfExIds.contains(item.exercise.exercise_id) && item.exercise.day == currentProgress.day && item.exercise.week == currentProgress.week) {
                listOfToday.add(item)
            }
        }
        return listOfToday
    }

    private fun changeProgressBarAndCompleted() {
        myView.findViewById<ProgressBar>(R.id.programProgressBar)
            .setProgress(currentProgress.week + 1 * (100 / MAX_WEEKS) * currentProgress.week, true)
        myView.findViewById<TextView>(R.id.programCompletedTV).text = getString(
            R.string.program_completed,
            (currentProgress.week + 1 / MAX_WEEKS) * (100 / MAX_WEEKS)
        )
    }

    private fun changeBlock() {
        myView.findViewById<TextView>(R.id.programBlockTV).text =
            getString(R.string.program_block, currentProgress.week + 1 / 4 + 1)
    }

    private fun changeWeek() {
        myView.findViewById<TextView>(R.id.currentWeekTV).text =
            getString(R.string.calendar_week, currentProgress.week + 1)
    }

    override fun onDateClick(pos: Int) {
        currentProgress.day = pos
        exerciseAdapter.setData(findTodayExercises())
        calendarAdapter.setData(pos)
    }

}