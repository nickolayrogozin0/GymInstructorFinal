package com.example.myapplication.fragments

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
import com.example.myapplication.*
import com.example.myapplication.adapters.Adapter
import com.example.myapplication.adapters.CalendarAdapter
import com.example.myapplication.database.MyDatabase
import com.google.android.flexbox.*
import com.example.myapplication.R


class MainFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick {

    private lateinit var myView: View
    private lateinit var myDB: MyDatabase
    private lateinit var exerciseAdapter: Adapter
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var currentProgress: CurrentProgress
    private lateinit var currentProgram: ProgramHasExercise

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_main, container, false)

        //Database initialization
        myDB = MyDatabase.getDatabase(requireContext())

        //Getting current progress dataclass and setting current values for query
        currentProgress = myDB.programDao().getCurrentProgress()
        currentProgram = myDB.programDao().getProgramWithExercisesById(currentProgress.program_id)

        //Calendar recycler initialization
        initCalendarRecycler()

        //Program block initialization
        initProgramBlock()

        //Exercise recycler initialization
        initExerciseRecycler()

        //Setting onClickListeners to switch weeks
        val right = myView.findViewById<ImageButton>(R.id.right)
        right.setOnClickListener {
            incWeek()
        }

        val left = myView.findViewById<ImageButton>(R.id.left)
        left.setOnClickListener {
            decWeek()
        }

        return myView
    }

    private fun initProgramBlock() {
        changeTitle()
        changeBlock()
        changeProgressBarAndCompleted()
    }

    private fun changeTitle() {
        myView.findViewById<TextView>(R.id.programTitleTV).text =
            currentProgram.program.program_title
    }

    private fun initExerciseRecycler() {
        val exerciseRecycler = myView.findViewById<RecyclerView>(R.id.recycler)
        exerciseAdapter = Adapter(requireContext())
        exerciseRecycler.adapter = exerciseAdapter
        exerciseRecycler.layoutManager = LinearLayoutManager(requireContext())
        exerciseAdapter.setData(todayExerciseQuery())
    }

    private fun initCalendarRecycler() {
        val calendarRecycler = myView.findViewById<RecyclerView>(R.id.calendar)
        calendarAdapter = CalendarAdapter(this, myDB.programDao().getTrainingDays())
        calendarRecycler.adapter = calendarAdapter
        val calendarLayout = FlexboxLayoutManager(requireContext())
        calendarLayout.justifyContent = JustifyContent.CENTER
        calendarLayout.alignItems = AlignItems.CENTER
        calendarLayout.flexDirection = FlexDirection.ROW
        calendarLayout.flexWrap = FlexWrap.WRAP
        calendarRecycler.layoutManager = calendarLayout
        calendarAdapter.setData(currentProgress.day)
        changeWeek()
    }

    private fun decWeek() {
        Log.i("WEEK", currentProgress.week.toString())
        if (currentProgress.week != 0) {
            currentProgress.week--
            exerciseAdapter.setData(
                todayExerciseQuery()
            )
        }
        changeWeek()
        changeProgressBarAndCompleted()
        if (currentProgress.week + 1 % 4 == 0) {
            changeBlock()
        }
    }

    private fun incWeek() {
        if (currentProgress.week < currentProgram.program.weeks - 1)
            currentProgress.week++
        exerciseAdapter.setData(
            todayExerciseQuery()
        )
        changeWeek()
        changeProgressBarAndCompleted()
        if (currentProgress.week + 1 % 4 == 0) {
            changeBlock()
        }
    }

    private fun changeProgressBarAndCompleted() {
        myView.findViewById<ProgressBar>(R.id.programProgressBar)
            .setProgress(currentProgress.week + 1 * (100 / currentProgram.program.weeks) * currentProgress.week, true)
        myView.findViewById<TextView>(R.id.programCompletedTV).text = getString(
            R.string.program_completed,
            (currentProgress.week + 1 / currentProgram.program.weeks) * (100 / currentProgram.program.weeks)
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
        exerciseAdapter.setData(
            todayExerciseQuery()
        )
        calendarAdapter.setData(pos)
    }

    private fun todayExerciseQuery(): List<ExerciseHasLoad> {
        return myDB.programDao().getExercisesHasLoadForToday(
            currentProgram.listOfExercises[0].exercise_id,
            currentProgram.listOfExercises.last().exercise_id,
            currentProgress.day,
            currentProgress.week
        )
    }

}