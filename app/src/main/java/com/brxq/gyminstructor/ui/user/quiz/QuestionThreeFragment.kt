package com.brxq.gyminstructor.ui.user.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentQuestionThreeBinding
import com.brxq.gyminstructor.databinding.FragmentQuestionTwoBinding

class QuestionThreeFragment : Fragment() {

    private lateinit var binding: FragmentQuestionThreeBinding

    private val args : QuestionThreeFragmentArgs by navArgs()

    private var selectedOption = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionThreeBinding.inflate(layoutInflater)

        binding.nextButton.isClickable = false

        binding.radioOne.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = 1
            if (binding.radioTwo.isChecked) binding.radioTwo.isChecked = false
            if (binding.radioThree.isChecked) binding.radioThree.isChecked = false
        }
        binding.radioTwo.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = 2
            if (binding.radioOne.isChecked) binding.radioOne.isChecked = false
            if (binding.radioThree.isChecked) binding.radioThree.isChecked = false
        }
        binding.radioThree.setOnClickListener {
            binding.nextButton.isClickable = true
            selectedOption = 3
            if (binding.radioOne.isChecked) binding.radioOne.isChecked = false
            if (binding.radioTwo.isChecked) binding.radioTwo.isChecked = false
        }

        binding.nextButton.setOnClickListener {
            if (selectedOption != -1) {
                val action =
                    QuestionThreeFragmentDirections.actionQuestionThreeFragmentToQuestionFourFragment(
                        args.q1a,
                        args.q2a,
                        selectedOption
                    )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}