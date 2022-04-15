package com.brxq.gyminstructor.ui.user.notes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.databinding.FragmentNotesBinding
import com.brxq.gyminstructor.model.Note


class NotesFragment : Fragment(), NoteClickInterface, NoteClickDeleteInterface {

    private val noteViewModel: NoteViewModel by activityViewModels()
    private lateinit var binding : FragmentNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val noteRVAdapter = NoteRVAdapter(requireContext(), this, this)

        binding.idRVNotes.adapter = noteRVAdapter
        binding.idRVNotes.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.allNotes.observe(viewLifecycleOwner){
            noteRVAdapter.updateList(it)
        }

        binding.idFABAddNote.setOnClickListener{
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment2(
                -1,
                "",
                "",
                false
            )
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentNotesBinding.inflate(layoutInflater)
    }

    override fun onNoteClick(note: Note) {
        val action =
            NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment2(
                note.id,
                note.noteTitle,
                note.noteDescription,
                true
            )
        findNavController().navigate(action)
    }

    override fun onDeleteIconClick(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(requireContext(), "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

}