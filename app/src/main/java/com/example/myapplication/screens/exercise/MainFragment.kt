package com.example.myapplication.screens.exercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.screens.exercise.calendar.CalendarAdapter
import com.example.myapplication.R
import com.example.myapplication.database.*
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.model.CurrentProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick,
    ExerciseAdapter.OnExerciseClick {

    private var binding: FragmentMainBinding? = null
    private var exerciseAdapter: ExerciseAdapter? = null
    private var calendarAdapter: CalendarAdapter? = null

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        initCalendarRecycler()
        initProgramBlock()
        initExerciseRecycler()

        binding?.left?.setOnClickListener { decWeek() }
        binding?.right?.setOnClickListener { incWeek() }

        return binding?.root
    }

    private fun decWeek() {

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { currentProgress ->
            if (currentProgress.week != 0) {
                currentProgress.week--
                viewModel.updateCurrentProgress(currentProgress)
                updateExerciseList(currentProgress)
            }
            changeWeek()
            changeProgressBarAndCompleted()
            if (currentProgress.week + 1 % 4 == 0) {
                changeBlock()
            }
        }

    }

    private fun incWeek() {

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { currentProgress ->
            viewModel.getProgramWithExercisesById(currentProgress.program_id)
                .observe(viewLifecycleOwner) { currentProgram ->
                    if (currentProgress.week < currentProgram!!.program.weeks - 1)
                        currentProgress.week++
                    viewModel.updateCurrentProgress(currentProgress)
                    updateExerciseList(currentProgress)
                    changeWeek()
                    changeProgressBarAndCompleted()
                    if (currentProgress!!.week + 1 % 4 == 0) {
                        changeBlock()
                    }
                }

        }

    }

    private fun initProgramBlock() {
        changeTitle()
        changeBlock()
        changeProgressBarAndCompleted()
    }

    private fun changeTitle() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { current ->
            viewModel
                .getProgramWithExercisesById(current.program_id)
                .observe(viewLifecycleOwner) { program ->
                    binding?.programTitleTV?.text = program.program.program_title
                }
        }
    }

    private fun changeProgressBarAndCompleted() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { current ->
            viewModel.getProgramWithExercisesById(current.program_id)
                .observe(viewLifecycleOwner) { program ->
                    binding?.programProgressBar?.setProgress(
                        current.week + 1 * (100 / program.program.weeks) * current.week,
                        true
                    )

                    binding?.programCompletedTV?.text = getString(
                        R.string.program_completed,
                        (current.week + 1 / program.program.weeks) *
                                (100 / program.program.weeks)
                    )
                }
        }
    }

    private fun changeBlock() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            binding?.programBlockTV?.text =
                getString(R.string.program_block, it.week + 1 / 4 + 1)
        }
    }

    private fun initExerciseRecycler() {
        exerciseAdapter = ExerciseAdapter(requireContext(), this)
        binding?.recycler?.adapter = exerciseAdapter
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            checkNotNull(it)
            updateExerciseList(it)
        }
    }

    private fun initCalendarRecycler() {

        Log.i("TRAINING_DAYS", viewModel.getTrainingDays().toString())

        calendarAdapter = CalendarAdapter(this)
        binding?.calendar?.adapter = calendarAdapter
        val layoutManager = object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        binding?.calendar?.layoutManager = layoutManager


        viewModel.getTrainingDays().observe(viewLifecycleOwner) {
            calendarAdapter!!.setTrainingDays(it)
        }

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            calendarAdapter?.setData(it.day)
        }

        changeWeek()
    }




    private fun changeWeek() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            binding?.currentWeekTV?.text =
                getString(R.string.calendar_week, it.week + 1)
        }
    }

    override fun onDateClick(pos: Int) {

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            checkNotNull(it)
            it.day = pos
            Log.i("CURRENT_PROGRESS", it.toString())
            viewModel.updateCurrentProgress(it)
            updateExerciseList(it)
            calendarAdapter?.setData(pos)
        }

    }

    private fun updateExerciseList(it: CurrentProgress) {
        viewModel.getExerciseForToday(
            it.program_id,
            it.day,
            it.week
        ).observe(viewLifecycleOwner) { list ->
            checkNotNull(list)
            Log.i("EXERCISE_LIST", list.toString())
            exerciseAdapter?.setData(list)
        }
    }

    override fun onFinishClick(pos: Int) {
        val viewHolder = binding?.recycler?.findViewHolderForAdapterPosition(pos)
        val itemView = viewHolder?.itemView
        itemView?.alpha = 0.4f
        val button = itemView?.findViewById<RadioButton>(R.id.finishButton)
        button?.isClickable = false
        button?.isChecked = true
        val exercise = exerciseAdapter!!.listOfExercisesHasLoad[pos].exercise
        exercise.isComplete = 1
        viewModel.finishExercise(exercise)
    }

}