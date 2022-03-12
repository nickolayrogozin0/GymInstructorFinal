package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.ExerciseAdapter
import com.example.myapplication.adapters.CalendarAdapter
import com.example.myapplication.R
import com.example.myapplication.database.*
import com.example.myapplication.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        initExerciseRecycler()

        return binding?.root
    }

    private fun initProgramBlock() {
        changeTitle()
        changeBlock()
        changeProgressBarAndCompleted()
    }

    private fun changeTitle() {
//        binding.programTitleTV.text =
//            currentProgram.program.program_title
    }

    private fun initExerciseRecycler() {
        exerciseAdapter = ExerciseAdapter(requireContext(), this)
        binding?.recycler?.adapter = exerciseAdapter
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
//        lifecycleScope.launchWhenStarted {
//            viewModel.getCurrentProgress().collectLatest { currentProgress ->
//
//                Log.i("EXERCISE_PROGRESS", currentProgress.toString())
//
//                viewModel.getExerciseForToday(
//                    currentProgress.program_id,
//                    currentProgress.day,
//                    currentProgress.week
//                ).collectLatest { list ->
//
//                    Log.i("EXERCISE_PROGRESS_LIST", list.toString())
//
//                    exerciseAdapter!!.setData(list)
//                }
//            }
//        }
        viewModel.getCurrentProgress().observe(viewLifecycleOwner){
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

        viewModel.getCurrentProgress().observe(viewLifecycleOwner){
            calendarAdapter?.setData(it.day)
        }

//        lifecycleScope.launchWhenStarted {
//
//            viewModel.getCurrentProgress().collectLatest {
//                calendarAdapter!!.setData(it.day)
//            }
//
//        }

        changeWeek()
    }

//    private fun decWeek() {
//        if (currentProgress != null) {
//            Log.i("WEEK", currentProgress!!.week.toString())
//            if (currentProgress!!.week != 0) {
//                currentProgress!!.week--
////                exerciseExerciseAdapter?.setData(
//////                    todayExerciseQuery()
////                )
//            }
//            changeWeek()
//            changeProgressBarAndCompleted()
//            if (currentProgress!!.week + 1 % 4 == 0) {
//                changeBlock()
//            }
//        }
//    }
//
//    private fun incWeek() {
//        if (currentProgress != null) {
//            if (currentProgress!!.week < currentProgram!!.program.weeks - 1)
//                currentProgress!!.week++
////            exerciseExerciseAdapter?.setData(
////                todayExerciseQuery()
////            )
//            changeWeek()
//            changeProgressBarAndCompleted()
//            if (currentProgress!!.week + 1 % 4 == 0) {
//                changeBlock()
//            }
//        }
//
//    }

    private fun changeProgressBarAndCompleted() {
//        binding.programProgressBar
//            .setProgress(
//                currentProgress.week + 1 * (100 / currentProgram.program.weeks) * currentProgress.week,
//                true
//            )
//        binding.programCompletedTV.text = getString(
//            R.string.program_completed,
//            (currentProgress.week + 1 / currentProgram.program.weeks) * (100 / currentProgram.program.weeks)
//        )
    }

    private fun changeBlock() {
//        binding.programBlockTV.text =
//            getString(R.string.program_block, currentProgress.week + 1 / 4 + 1)
    }

    private fun changeWeek() {
//        binding.currentWeekTV.text =
//            getString(R.string.calendar_week, currentProgress.week + 1)
    }

    override fun onDateClick(pos: Int) {

        viewModel.getCurrentProgress().observe(viewLifecycleOwner){
            checkNotNull(it)
            it.day = pos
            Log.i("CURRENT_PROGRESS", it.toString())
            viewModel.updateCurrentProgress(it)
            updateExerciseList(it)
            calendarAdapter?.setData(pos)
        }

//        lifecycleScope.launchWhenStarted {
//            viewModel.getCurrentProgress().collectLatest {
//                it.day = pos
//                Log.i("CURRENT_PROGRESS", it.toString())
//                viewModel.updateCurrentProgress(it)
//                calendarAdapter?.setData(pos)
//            }
//        }
//
//        lifecycleScope.launchWhenStarted {
//            viewModel.getCurrentProgress().collectLatest {
//                viewModel.getExerciseForToday(
//                    it.program_id,
//                    it.day,
//                    it.week
//                ).collectLatest {  list ->
//                    exerciseAdapter?.setData(list)
//                }
//            }
//        }

    }

    private fun updateExerciseList(it: CurrentProgress) {
        viewModel.getExerciseForToday(
            it.program_id,
            it.day,
            it.week
        ).observe(viewLifecycleOwner){ list->
            checkNotNull(list)
            exerciseAdapter?.setData(list)
        }
    }

//    private fun todayExerciseQuery(): List<ExerciseHasLoad> {
//        if (currentProgress != null && currentProgram != null){
//            return viewModel.getExercisesHasLoadForToday(
//                currentProgram!!.listOfExercises[0].exercise_id,
//                currentProgram!!.listOfExercises.last().exercise_id,
//                currentProgress!!.day,
//                currentProgress!!.week
//            )
//        }
//        return emptyList()
//    }

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