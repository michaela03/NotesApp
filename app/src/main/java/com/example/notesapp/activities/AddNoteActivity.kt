package com.example.notesapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.data.AppDatabase
import com.example.notesapp.models.Note
import com.example.notesapp.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val body = binding.etBody.text.toString()
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            if (title.isNotEmpty() && body.isNotEmpty()) {
                val note = Note(title = title, body = body, date = date)
                lifecycleScope.launch {
                    db.noteDao().insert(note)
                    Toast.makeText(this@AddNoteActivity, "Бележката е добавена!", Toast.LENGTH_SHORT).show()
                    finish() // Затваря активността и се връща назад
                }
            } else {
                Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
