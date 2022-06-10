package com.brxq.gyminstructor.ui.programs

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentProgramBinding
import com.brxq.gyminstructor.model.CurrentProgress
import com.brxq.gyminstructor.ui.exercise.ExerciseFragment
import com.brxq.gyminstructor.ui.exercise.ExerciseViewModel

class ProgramFragment : Fragment(), ProgramListAdapter.OnProgramClick {

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()
    private val programViewModel: ProgramViewModel by activityViewModels()
    private var binding: FragmentProgramBinding? = null
    private var programListAdapter: ProgramListAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgramBinding.inflate(layoutInflater)

        programListAdapter = ProgramListAdapter(this)
        binding?.programs?.adapter = programListAdapter
        binding?.programs?.layoutManager = LinearLayoutManager(requireContext())

        programViewModel.allPrograms.observe(viewLifecycleOwner){
            programListAdapter?.setData(it)
        }

        return binding?.root
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
                        exerciseViewModel.updateCurrentProgress(
                            it
                        )
                    }
                    findNavController().navigate(ProgramFragmentDirections.actionProgramFragmentToMainFragment())
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    override fun learnMore(adapterPosition: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val item = binding?.programs?.findViewHolderForAdapterPosition(adapterPosition)
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