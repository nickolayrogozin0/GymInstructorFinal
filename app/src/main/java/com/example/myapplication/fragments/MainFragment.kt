package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.adapters.CalendarAdapter
import com.example.myapplication.database.ProgramsAndExercisesDatabase
import com.google.android.flexbox.*
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding


class MainFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick,
    ExerciseAdapter.OnExerciseClick {

    private lateinit var binding: FragmentMainBinding
    private lateinit var programsAndExercisesDB: ProgramsAndExercisesDatabase
    private lateinit var exerciseExerciseAdapter: ExerciseAdapter
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var currentProgress: CurrentProgress
    private lateinit var currentProgram: ProgramHasExercise

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        //Database initialization
        programsAndExercisesDB = ProgramsAndExercisesDatabase.getDatabase(requireContext())

        //Getting current progress dataclass and setting current values for query
        currentProgress = programsAndExercisesDB.programDao().getCurrentProgress()
        currentProgram = programsAndExercisesDB.programDao()
            .getProgramWithExercisesById(currentProgress.program_id)

        //Calendar recycler initialization
        initCalendarRecycler()

        //Program block initialization
        initProgramBlock()

        //Exercise recycler initialization
        initExerciseRecycler()

        //Setting onClickListeners to switch weeks
        binding.right.setOnClickListener {
            incWeek()
        }

        binding.left.setOnClickListener {
            decWeek()
        }

        return binding.root
    }

    private fun initProgramBlock() {
        changeTitle()
        changeBlock()
        changeProgressBarAndCompleted()
    }

    private fun changeTitle() {
        binding.programTitleTV.text =
            currentProgram.program.program_title
    }

    private fun initExerciseRecycler() {
        exerciseExerciseAdapter = ExerciseAdapter(requireContext(), this)
        binding.recycler.adapter = exerciseExerciseAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        exerciseExerciseAdapter.setData(todayExerciseQuery())
    }

    private fun initCalendarRecycler() {
        val calendarAdapter =
            CalendarAdapter(this, programsAndExercisesDB.programDao().getTrainingDays())
        binding.calendar.adapter = calendarAdapter
        val layoutManager = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        binding.calendar.layoutManager = layoutManager
        calendarAdapter.setData(currentProgress.day)
        changeWeek()
    }

    private fun decWeek() {
        Log.i("WEEK", currentProgress.week.toString())
        if (currentProgress.week != 0) {
            currentProgress.week--
            exerciseExerciseAdapter.setData(
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
        exerciseExerciseAdapter.setData(
            todayExerciseQuery()
        )
        changeWeek()
        changeProgressBarAndCompleted()
        if (currentProgress.week + 1 % 4 == 0) {
            changeBlock()
        }
    }

    private fun changeProgressBarAndCompleted() {
        binding.programProgressBar
            .setProgress(
                currentProgress.week + 1 * (100 / currentProgram.program.weeks) * currentProgress.week,
                true
            )
        binding.programCompletedTV.text = getString(
            R.string.program_completed,
            (currentProgress.week + 1 / currentProgram.program.weeks) * (100 / currentProgram.program.weeks)
        )
    }

    private fun changeBlock() {
        binding.programBlockTV.text =
            getString(R.string.program_block, currentProgress.week + 1 / 4 + 1)
    }

    private fun changeWeek() {
        binding.currentWeekTV.text =
            getString(R.string.calendar_week, currentProgress.week + 1)
    }

    override fun onDateClick(pos: Int) {
        currentProgress.day = pos
        exerciseExerciseAdapter.setData(
            todayExerciseQuery()
        )
        calendarAdapter.setData(pos)
    }

    private fun todayExerciseQuery(): List<ExerciseHasLoad> {
        return programsAndExercisesDB.programDao().getExercisesHasLoadForToday(
            currentProgram.listOfExercises[0].exercise_id,
            currentProgram.listOfExercises.last().exercise_id,
            currentProgress.day,
            currentProgress.week
        )
    }

    override fun onFinishClick(pos: Int) {
        val viewHolder = binding.recycler.findViewHolderForAdapterPosition(pos)
        val itemView = viewHolder?.itemView
        itemView?.alpha = 0.4f
        val button = itemView?.findViewById<RadioButton>(R.id.finishButton)
        button?.isClickable = false
        button?.isChecked = true
        val exercise = exerciseExerciseAdapter.listOfExercisesHasLoad[pos].exercise
        exercise.isComplete = 1
        programsAndExercisesDB.programDao().finishExercise(
            exercise
        )
    }

}