package com.sellproject.SharedPreferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sellproject.MainActivity
import com.sellproject.R

class DetailsActivity : AppCompatActivity() {
    private lateinit var prf: SharedPreferences
    private lateinit var intentToMain: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
        val result = findViewById<TextView>(R.id.resultView)
        val btnLogOut = findViewById<Button>(R.id.btnLogOut)
        prf = getSharedPreferences("user_details", MODE_PRIVATE)
        intentToMain = Intent(this, MainActivity::class.java)
        result.text = "Hello, ${prf.getString("username", null)}"

        btnLogOut.setOnClickListener {
            prf.edit().clear().apply()
            startActivity(intentToMain)
        }
    }
}