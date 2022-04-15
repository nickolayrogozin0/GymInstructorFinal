package com.brxq.gyminstructor.ui.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {

    private var binding : FragmentStatisticsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

}