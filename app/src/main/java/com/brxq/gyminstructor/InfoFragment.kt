package com.brxq.gyminstructor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brxq.gyminstructor.databinding.FragmentInfoBinding
import com.brxq.gyminstructor.databinding.FragmentProgramBinding


class InfoFragment : Fragment() {

    private lateinit var binding : FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater)

        binding.rankAlt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://russia-powerlifting.ru/normativy"))
            startActivity(intent)
        }

        binding.rankIpf.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://fpr-info.ru/razr_norm.htm"))
            startActivity(intent)
        }

        binding.rulesAlt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://russia-powerlifting.ru/documenty/tehnicheskie-pravila"))
            startActivity(intent)
        }



        binding.rulesIpf.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://fpr-info.ru/___dokum/pravila_2018_2019.doc"))
            startActivity(intent)
        }

        return binding.root
    }


}