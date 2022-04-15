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
import androidx.recyclerview.widget.LinearLayoutManager
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentProgramBinding
import com.brxq.gyminstructor.model.CurrentProgress
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

        programViewModel.allPrograms.observe(viewLifecycleOwner) {
            programListAdapter?.setData(it)
        }

        return binding?.root
    }

    override fun selectProgram(adapterPosition: Int) {
//        val itemClickedOn = exerciseAdapter!!.listOfExercisesHasLoad[pos].exercise
        val p = CurrentProgress(0, 2,1,0)
//        itemClickedOn.isComplete = 1
//        viewModel.finishExercise(itemClickedOn)
        exerciseViewModel.updateCurrentProgress(p)
    }

    override fun learnMore(adapterPosition: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val item = binding?.programs?.findViewHolderForAdapterPosition(adapterPosition)
        builder.setMessage(
            item?.itemView?.findViewById<TextView>(R.id.programItemTitleTV)?.text
        )
            .setNegativeButton("Close",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

}