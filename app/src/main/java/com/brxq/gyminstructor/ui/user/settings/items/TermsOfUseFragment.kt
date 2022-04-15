package com.brxq.gyminstructor.ui.user.settings.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brxq.gyminstructor.databinding.FragmentTermsOfUseBinding

class TermsOfUseFragment : Fragment() {

    private lateinit var binding : FragmentTermsOfUseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsOfUseBinding.inflate(layoutInflater)



        return binding.root
    }

}