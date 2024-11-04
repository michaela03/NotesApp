package com.example.notesapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.activities.AddNoteActivity
import com.example.notesapp.activities.UpdateAndDeleteActivity
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.data.AppDatabase
import com.example.notesapp.models.Note
import com.example.notesapp.databinding.ListViewFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewFragment : Fragment() {
    private var _binding: ListViewFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase
    private lateinit var noteAdapter: NoteAdapter
    private val notesList = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListViewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
        noteAdapter = NoteAdapter(notesList) { note ->
            openNoteDetail(note)
        }

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = noteAdapter

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(requireContext(), AddNoteActivity::class.java))
        }

        loadNotes()
    }

    private fun loadNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getAllNotes()
            notesList.clear()
            notesList.addAll(notes)
            withContext(Dispatchers.Main) {
                noteAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun openNoteDetail(note: Note) {
        val intent = Intent(activity, UpdateAndDeleteActivity::class.java)
        intent.putExtra("NOTE", note)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
