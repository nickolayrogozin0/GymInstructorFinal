package com.brxq.gyminstructor.ui.user.quiz

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentQuizProgramsBinding
import com.brxq.gyminstructor.ui.exercise.ExerciseViewModel
import com.brxq.gyminstructor.ui.programs.ProgramListAdapter
import com.brxq.gyminstructor.ui.programs.ProgramViewModel


class QuizProgramsFragment : Fragment(), ProgramListAdapter.OnProgramClick {

    private val args: QuizProgramsFragmentArgs by navArgs()
    private lateinit var binding: FragmentQuizProgramsBinding
    private var programListAdapter: ProgramListAdapter? = null
    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val programViewModel: ProgramViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizProgramsBinding.inflate(layoutInflater)

        programListAdapter = ProgramListAdapter(this)
        binding.programs.adapter = programListAdapter
        binding.programs.layoutManager = LinearLayoutManager(requireContext())

        val td = if (args.q3a == 1) 3 else 0

        programViewModel.getProgramsViaQuiz(
            args.q2a,
            td
        ).observe(viewLifecycleOwner){
            programListAdapter!!.setData(it)
        }


        return binding.root
    }

    override fun selectProgram(adapterPosition: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(
            "Вы уверены, что хотите выбрать программу ${programListAdapter?.listOfPrograms?.get(adapterPosition)?.program_title}?"
        )
            .setNegativeButton("Закрыть",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
            .setPositiveButton("Выбрать",
                DialogInterface.OnClickListener { dialog, id ->
                    exerciseViewModel.getCurrentProgress().observe(viewLifecycleOwner){
                        it.program_id = programListAdapter?.listOfPrograms?.get(adapterPosition)?.program_id!!
                        it.max_squat = args.q4s
                        it.max_bench = args.q4b
                        it.max_deadlift = args.q4d
                        exerciseViewModel.updateCurrentProgress(
                            it
                        )
                    }
                    findNavController().navigate(QuizProgramsFragmentDirections.actionQuizProgramsFragmentToMainFragment())
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    override fun learnMore(adapterPosition: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val item = binding.programs.findViewHolderForAdapterPosition(adapterPosition)
        builder.setMessage(
            programListAdapter?.listOfPrograms?.get(adapterPosition)?.description
        )
            .setNegativeButton("Закрыть",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }


}