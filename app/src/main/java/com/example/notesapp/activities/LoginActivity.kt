package com.example.notesapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.data.AppDatabase
import com.example.notesapp.models.User
import com.example.notesapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализиране на View Binding и базата данни
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getDatabase(this)

        // Обработчик за кликване на бутона за вход
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Проверка на входните данни
            lifecycleScope.launch {
                val user = db.userDao().getUser(username, password)
                if (user != null) {
                    // Успешен вход - стартирай MainActivity
                    startMainActivity()
                } else {
                    // Невалидни данни - покажи съобщение за грешка
                    Toast.makeText(this@LoginActivity, "Невалидно потребителско име или парола", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Обработчик за кликване на бутона за регистрация
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Добави нов потребител
                val newUser = User(username = username, password = password)
                lifecycleScope.launch {
                    db.userDao().insert(newUser)
                    Toast.makeText(this@LoginActivity, "Регистрацията е успешна!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Моля, въведете потребителско име и парола", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Метод за стартиране на MainActivity
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Затваря LoginActivity, така че да не се връща назад
    }
}
