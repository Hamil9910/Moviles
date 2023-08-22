package com.example.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val button: Button = findViewById(R.id.button2)
        button.setOnClickListener {
            // Aquí definimos la acción al hacer clic en el botón
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}