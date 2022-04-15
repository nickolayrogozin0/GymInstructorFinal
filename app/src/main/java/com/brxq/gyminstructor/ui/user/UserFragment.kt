package com.brxq.gyminstructor.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentUserBinding


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_settingsFragment)
        }
        binding.calculatorsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_barbellCalculatorFragment)
        }
        binding.notesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_notesFragment)
        }
        return binding.root
    }
}