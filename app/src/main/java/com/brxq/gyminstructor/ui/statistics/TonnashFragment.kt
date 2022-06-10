package com.brxq.gyminstructor.ui.statistics


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.brxq.gyminstructor.databinding.TonnashFragmentBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class TonnashFragment : Fragment() {


    private var binding: TonnashFragmentBinding? = null

    private var viewModel: StatiscticsProgramChartViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TonnashFragmentBinding.inflate(layoutInflater)

        binding?.chart?.setTouchEnabled(false)
        binding?.chart?.setDragEnabled(false)
        binding?.chart?.setScaleEnabled(false)
        binding?.chart?.setScaleXEnabled(false)
        binding?.chart?.setScaleYEnabled(false)
        binding?.chart?.setPinchZoom(false)
        binding?.chart?.setDoubleTapToZoomEnabled(false)

        binding?.chart?.xAxis?.setAxisLineColor(Color.TRANSPARENT)
        binding?.chart?.axisLeft?.setAxisLineColor(Color.TRANSPARENT)
        binding?.chart?.axisRight?.setAxisLineColor(Color.TRANSPARENT)

        val entries: MutableList<Entry> = ArrayList()

        entries.add(Entry(1f, 17193F))
        entries.add(Entry(2f, 16063F))
        entries.add(Entry(3f, 9885F))
        entries.add(Entry(4f, 15123F))

        val dataSet = LineDataSet(entries, null)

        binding?.chart?.axisLeft?.textColor = Color.WHITE
        binding?.chart?.axisRight?.textColor = Color.WHITE
        binding?.chart?.xAxis?.textColor = Color.WHITE

        binding?.chart?.axisLeft?.textSize = 12f
        binding?.chart?.axisRight?.textSize = 12f
        binding?.chart?.xAxis?.textSize = 12f

        binding?.chart?.xAxis?.setGranularity(1f)

        binding?.chart?.legend?.isEnabled = false

//        binding?.chart?.axisRight?.enableGridDashedLine(10f, 5f,0f)
//        binding?.chart?.axisLeft?.enableGridDashedLine(10f, 5f,0f)
//        binding?.chart?.xAxis?.enableGridDashedLine(10f, 5f,0f)



        binding?.chart?.setExtraOffsets(0f,8f,0f,0f)

        val lineData = LineData(dataSet)
        lineData.setValueTextColor(Color.TRANSPARENT)

        binding?.chart?.data = lineData
        binding?.chart?.invalidate()

        return binding?.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatiscticsProgramChartViewModel::class.java)
    }

}

