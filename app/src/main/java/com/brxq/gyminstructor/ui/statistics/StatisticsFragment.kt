package com.brxq.gyminstructor.ui.statistics


import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {

    private var binding: FragmentStatisticsBinding? = null

    private var tonnash = true
    private var oi = false
    private var kpsh = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)

        changeColors()
        changeVis()
        binding?.tonTV?.setOnClickListener {
            tonnash = true
            oi = false
            kpsh = false
            changeVis()
            changeColors()
        }

        binding?.oiTV?.setOnClickListener {
            oi = true
            tonnash = false
            changeVis()
            changeColors()
        }

        binding?.kpTV?.setOnClickListener {
            kpsh = true
            tonnash = false
            oi = false
            changeVis()
            changeColors()
        }


        return binding?.root
    }

    private fun changeVis() {
        if (!tonnash){
            binding?.fragmentContainerView1?.visibility = View.GONE
        }else {
            binding?.fragmentContainerView1?.visibility = View.VISIBLE
        }
        if (!oi){
            binding?.fragmentContainerView2?.visibility = View.GONE
        }else {
            binding?.fragmentContainerView2?.visibility = View.VISIBLE
        }
        if (!kpsh){
            binding?.fragmentContainerView3?.visibility = View.GONE
        }else {
            binding?.fragmentContainerView3?.visibility = View.VISIBLE
        }

    }


    private fun changeColors() {
        if (tonnash){
            binding?.tonTV?.setTextColor(resources.getColor(R.color.cyan_ish))
        }else {
            binding?.tonTV?.setTextColor(resources.getColor(R.color.white))
        }
        if (oi){
            binding?.oiTV?.setTextColor(resources.getColor(R.color.cyan_ish))
        }else {
            binding?.oiTV?.setTextColor(resources.getColor(R.color.white))
        }
        if (kpsh){
            binding?.kpTV?.setTextColor(resources.getColor(R.color.cyan_ish))
        }else {
            binding?.kpTV?.setTextColor(resources.getColor(R.color.white))
        }
    }

}