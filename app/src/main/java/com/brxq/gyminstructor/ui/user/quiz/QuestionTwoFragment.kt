package com.brxq.gyminstructor.ui.user.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentQuestionOneBinding
import com.brxq.gyminstructor.databinding.FragmentQuestionTwoBinding


class QuestionTwoFragment : Fragment() {

    private lateinit var binding: FragmentQuestionTwoBinding

    private val args : QuestionTwoFragmentArgs by navArgs()

    private var selectedOption = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionTwoBinding.inflate(layoutInflater)

        binding.nextButton.isClickable = false

        binding.radioOne.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = "strength"
            if (binding.radioTwo.isChecked) binding.radioTwo.isChecked = false
            if (binding.radioThree.isChecked) binding.radioThree.isChecked = false
        }
        binding.radioTwo.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = "endurance"
            if (binding.radioOne.isChecked) binding.radioOne.isChecked = false
            if (binding.radioThree.isChecked) binding.radioThree.isChecked = false
        }
        binding.radioThree.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = "hypertrophy"
            if (binding.radioOne.isChecked) binding.radioOne.isChecked = false
            if (binding.radioTwo.isChecked) binding.radioTwo.isChecked = false
        }

        binding.nextButton.setOnClickListener {
            if (selectedOption != "") {
                val action =
                    QuestionTwoFragmentDirections.actionQuestionTwoFragmentToQuestionThreeFragment(
                        args.q1a,
                        selectedOption
                    )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}