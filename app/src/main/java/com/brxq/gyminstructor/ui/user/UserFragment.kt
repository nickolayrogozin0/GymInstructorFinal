package com.brxq.gyminstructor.ui.user

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentUserBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Collections.min
import kotlin.math.min


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (data != null) binding.userCircleView.setImageURI(data.data)
            }
        }

        binding.userCircleView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        binding.findProgramBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_questionOneFragment)
        }

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

