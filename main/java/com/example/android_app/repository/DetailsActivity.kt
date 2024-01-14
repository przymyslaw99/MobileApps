package com.example.android_app.repository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.android_app.R

class DetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("CUSTOM_ID")

        Toast.makeText(this, "details id: $id", Toast.LENGTH_SHORT).show()
    }
}