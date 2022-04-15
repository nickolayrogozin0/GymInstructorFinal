package com.brxq.gyminstructor.ui.exercise

import android.os.Bundle


import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.brxq.gyminstructor.R


import com.brxq.gyminstructor.databinding.FragmentExerciseBinding
import com.brxq.gyminstructor.model.CurrentProgress


import com.brxq.gyminstructor.model.TrainingDays
import com.brxq.gyminstructor.ui.exercise.calendar.CalendarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick,
    ExerciseAdapter.OnExerciseClick {

    private val viewModel: ExerciseViewModel by viewModels()
    private var binding: FragmentExerciseBinding? = null
    private var calendarAdapter: CalendarAdapter? = null
    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseBinding.inflate(layoutInflater)

        initCalendarAdapter()
        initProgramBlock()
        initExerciseRecycler()

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            setExerciseForToday(it)
        }

        binding?.left?.setOnClickListener {
            viewModel.changeWeek(-1)
            changeWeek()
            initProgramBlock()
        }

        binding?.right?.setOnClickListener {
            viewModel.changeWeek(1)
            changeWeek()
            initProgramBlock()
        }

        return binding?.root
    }

    private fun changeWeek() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            binding?.currentWeekTV?.text =
                getString(R.string.exercise_calendar_current_week, it.week + 1)
        }
    }

    private fun initCalendarAdapter() {
        calendarAdapter = CalendarAdapter(
            requireContext(),
            this,
            TrainingDays(
                0,
                0,
                1,
                0,
                1,
                0,
                1,
                0,
            )
        )
        binding?.calendarRecyclerView?.adapter = calendarAdapter

        val calendarLayoutManage =
            object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }

        binding?.calendarRecyclerView?.layoutManager = calendarLayoutManage

        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            calendarAdapter?.setData(it.day)
        }

        changeWeek()
    }

    private fun initExerciseRecycler() {
        exerciseAdapter = ExerciseAdapter(requireContext(), this)
        binding?.exerciseRecyclerView?.adapter = exerciseAdapter

        val exerciseLayoutManager =
            object : LinearLayoutManager(requireContext(), VERTICAL, false) {

            }

        binding?.exerciseRecyclerView?.layoutManager = exerciseLayoutManager
    }


    private fun initProgramBlock() {
        changeTitle()
        changeBlock()
        changeProgressBarAndCompleted()
    }

    private fun changeTitle() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { current ->
            viewModel.getProgramById(current.program_id).observe(viewLifecycleOwner) { program ->
                binding?.programTitleTV?.text = program.program_title
                binding?.programBlockTV
            }
        }
    }

    private fun changeProgressBarAndCompleted() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { current ->
            viewModel.getProgramById(current.program_id)
                .observe(viewLifecycleOwner) { program ->
                    binding?.programProgressBar?.setProgress(
                        current.week + 1 * (100 / program.weeks) * current.week,
                        true
                    )

                    binding?.programCompletedTV?.text = getString(
                        R.string.exercise_block_completed,
                        (current.week + 1 / program.weeks) *
                                (100 / program.weeks)
                    )
                }
        }
    }

    private fun changeBlock() {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) {
            binding?.programBlockTV?.text =
                getString(R.string.exercise_block_block_number, it.week + 1 / 4 + 1)
        }
    }

    private fun setExerciseForToday(curr: CurrentProgress) {
        viewModel.getTodayExercise(
            curr.program_id,
            curr.day,
            curr.week
        ).observe(viewLifecycleOwner) { list ->
            checkNotNull(list)
            exerciseAdapter?.setData(list)
        }
    }

    override fun onDateClick(pos: Int) {
        viewModel.getCurrentProgress().observe(viewLifecycleOwner) { curr ->
            checkNotNull(curr)
            curr.day = pos
            calendarAdapter?.setData(pos)
            viewModel.updateCurrentProgress(curr)
            setExerciseForToday(curr)
        }

    }

    override fun onFinishClick(pos: Int) {
        val itemClickedOn = exerciseAdapter!!.listOfExercisesHasLoad[pos].exercise
        itemClickedOn.isComplete = 1
        viewModel.finishExercise(itemClickedOn)
        exerciseAdapter?.updateData(pos)
    }

}