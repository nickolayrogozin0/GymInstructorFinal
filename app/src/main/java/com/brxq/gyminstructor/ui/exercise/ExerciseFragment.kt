package com.brxq.gyminstructor.ui.exercise

import android.os.Bundle


import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager


import com.brxq.gyminstructor.databinding.FragmentExerciseBinding
import com.brxq.gyminstructor.model.CurrentProgress

import com.brxq.gyminstructor.model.TrainingDays
import com.brxq.gyminstructor.ui.exercise.calendar.CalendarAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment(), CalendarAdapter.ViewHolder.OnDateClick, ExerciseAdapter.OnExerciseClick {

    private val viewModel: ExerciseViewModel by viewModels()
    private var binding: FragmentExerciseBinding? = null
    private var calendarAdapter : CalendarAdapter? = null
    private var exerciseAdapter : ExerciseAdapter? = null

    private var currentProgress : CurrentProgress? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseBinding.inflate(layoutInflater)

        //Init current progress



        //Init calendar

        calendarAdapter = CalendarAdapter(this,
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

        val calendarLayoutManage = object : LinearLayoutManager(requireContext(), HORIZONTAL, false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }

        binding?.calendarRecyclerView?.layoutManager = calendarLayoutManage

        //

        //Init exercise recycler

        exerciseAdapter = ExerciseAdapter(requireContext(), this)
        binding?.exerciseRecyclerView?.adapter = exerciseAdapter

        val exerciseLayoutManager = object : LinearLayoutManager(requireContext(), VERTICAL, false){

        }

        binding?.exerciseRecyclerView?.layoutManager = exerciseLayoutManager

        //Setting current day exercises

        viewModel.getTodayExercise()?.observe(viewLifecycleOwner){
            if (it!=null){
                exerciseAdapter!!.setData(it)
            }else {
                Toast.makeText(requireContext(), "NO EXERCISE TODAY", Toast.LENGTH_SHORT).show()
            }
        }


//        exerciseAdapter?.setData(exerciseList)
//        viewModel.allExercise.observe(viewLifecycleOwner) {
//            exerciseAdapter?.setData(it)
//        }
        return binding?.root
    }

    override fun onDateClick(pos: Int) {
        calendarAdapter?.setData(pos)
        calendarAdapter?.notifyDataSetChanged()
    }

    override fun onFinishClick(pos: Int) {
        TODO("Not yet implemented")
    }


}