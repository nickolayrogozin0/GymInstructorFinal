package com.brxq.gyminstructor.ui.user.notes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brxq.gyminstructor.databinding.FragmentAddEditNoteBinding
import com.brxq.gyminstructor.model.Note
import java.text.SimpleDateFormat
import java.util.*


class AddEditNoteFragment : Fragment() {

    private val args: AddEditNoteFragmentArgs by navArgs()

    private val noteViewModel: NoteViewModel by activityViewModels()

    lateinit var binding: FragmentAddEditNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        binding = FragmentAddEditNoteBinding.inflate(layoutInflater)

        if (args.isEdit) {
            binding.idEditNoteTitle.setText(args.title)
            binding.idEditNoteDescription.setText(args.body)
            binding.idBtnUpdate.text = "Update"
        } else {
            binding.idBtnUpdate.text = "Save note"
        }

        binding.idBtnUpdate.setOnClickListener {

            if (binding.idEditNoteTitle.text.isNotEmpty() && binding.idEditNoteDescription.text.isNotEmpty()) {
                val sdf = SimpleDateFormat("dd MMM, yyy - HH:mm")
                val currentDate: String = sdf.format(Date())

                if (args.isEdit) {
                    val updateNote = Note(
                        binding.idEditNoteTitle.text.toString(),
                        binding.idEditNoteDescription.text.toString(),
                        currentDate
                    )
                    updateNote.id = args.id
                    noteViewModel.updateNote(updateNote)
                    Toast.makeText(requireContext(), "Note Update..", Toast.LENGTH_LONG).show()
                } else {
                    if (binding.idEditNoteTitle.text.isNotEmpty() && binding.idEditNoteDescription.text.isNotEmpty()) {
                        noteViewModel.addNote(
                            Note(
                                binding.idEditNoteDescription.text.toString(),
                                binding.idEditNoteDescription.text.toString(),
                                currentDate
                            )
                        )
                        Toast.makeText(requireContext(), "Note Added..", Toast.LENGTH_LONG).show()
                    }
                }
            }

            findNavController().navigate(AddEditNoteFragmentDirections.actionAddEditNoteFragmentToNotesFragment())

        }
    }

}