package com.sellproject.SharedPreferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sellproject.SharedPreferences.DetailsActivity
import com.sellproject.R

class MainActivity1 : AppCompatActivity() {
    private lateinit var uname: EditText
    private lateinit var pwd: EditText
    private lateinit var loginBtn: Button
    private lateinit var pref: SharedPreferences
    private lateinit var intentToDetails: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        uname = findViewById(R.id.txtName)
        pwd = findViewById(R.id.txtPwd)
        loginBtn = findViewById(R.id.btnLogin)
        pref = getSharedPreferences("user_details", MODE_PRIVATE)
        intentToDetails = Intent(this, DetailsActivity::class.java)

        if (pref.contains("username") && pref.contains("password")) {
            startActivity(intentToDetails)
        }

        loginBtn.setOnClickListener {
            val username = uname.text.toString()
            val password = pwd.text.toString()
            if (username == "admin" && password == "admin") {
                with(pref.edit()) {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(intentToDetails)
            } else {
                Toast.makeText(applicationContext, "Credentials are not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}