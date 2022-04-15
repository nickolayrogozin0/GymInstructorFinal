package com.brxq.gyminstructor.ui.user.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.settingsNDABtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_NDAFragment)
        }

        binding.settingsUsageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_termsOfUseFragment)
        }

        binding.settingsQuestionsBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "nickolayrogozin0@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Question about GymInstructor")
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, got some questions about GI. *YOUR QUESTION*")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                //TODO smth
            }
        }

        return binding.root
    }


}