package com.example.notesapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.data.AppDatabase
import com.example.notesapp.models.Note
import com.example.notesapp.databinding.ActivityUpdateAndDeleteBinding
import kotlinx.coroutines.launch

class UpdateAndDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAndDeleteBinding
    private lateinit var db: AppDatabase
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAndDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        // Получаване на бележката от намерението
        note = intent.getSerializableExtra("NOTE") as Note

        binding.etTitle.setText(note.title)
        binding.etBody.setText(note.body)

        binding.btnUpdate.setOnClickListener {
            val updatedTitle = binding.etTitle.text.toString()
            val updatedBody = binding.etBody.text.toString()

            if (updatedTitle.isNotEmpty() && updatedBody.isNotEmpty()) {
                lifecycleScope.launch {
                    db.noteDao().update(note.copy(title = updatedTitle, body = updatedBody))
                    Toast.makeText(this@UpdateAndDeleteActivity, "Бележката е актуализирана!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                db.noteDao().delete(note)
                Toast.makeText(this@UpdateAndDeleteActivity, "Бележката е изтрита!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
