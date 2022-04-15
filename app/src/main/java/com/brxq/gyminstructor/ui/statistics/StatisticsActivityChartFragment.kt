package com.brxq.gyminstructor.ui.statistics

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brxq.gyminstructor.R

class StatisticsActivityChartFragment : Fragment() {

    companion object {
        fun newInstance() = StatisticsActivityChartFragment()
    }

    private lateinit var viewModel: StatisticsActivityChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.statistics_activity_chart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatisticsActivityChartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}