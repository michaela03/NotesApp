package com.example.notesapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.fragments.ListViewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListViewFragment())
                .commit()
        }
    }
}
