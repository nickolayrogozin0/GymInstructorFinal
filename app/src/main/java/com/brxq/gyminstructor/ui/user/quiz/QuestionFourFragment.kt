package com.brxq.gyminstructor.ui.user.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentQuestionFourBinding


class QuestionFourFragment : Fragment() {

    private lateinit var binding: FragmentQuestionFourBinding
    private val args: QuestionFourFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionFourBinding.inflate(layoutInflater)

        binding.showButton.setOnClickListener {

            if (
                !binding.radioOne.text.isNullOrEmpty() &&
                binding.radioOne.text.toString().toFloat() > 0.0 &&
                !binding.radioTwo.text.isNullOrEmpty() &&
                binding.radioTwo.text.toString().toFloat() > 0.0 &&
                !binding.radioThree.text.isNullOrEmpty() &&
                binding.radioThree.text.toString().toFloat() > 0.0
            ) {
                val action = QuestionFourFragmentDirections.actionQuestionFourFragmentToQuizProgramsFragment(
                    args.q1a,
                    args.q2a,
                    args.q3a,
                    binding.radioOne.text.toString().toFloat(),
                    binding.radioTwo.text.toString().toFloat(),
                    binding.radioThree.text.toString().toFloat()
                )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

}