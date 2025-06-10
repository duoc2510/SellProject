package com.sellproject.InternalStorage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sellproject.R


class MainActivity : AppCompatActivity() {
    private lateinit var uname: EditText
    private lateinit var pwd: EditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        uname = findViewById(R.id.txtName)
        pwd = findViewById(R.id.txtPwd)
        saveBtn = findViewById(R.id.btnSave)

        saveBtn.setOnClickListener {
            val username = uname.text.toString() + "\n"
            val password = pwd.text.toString()
            try {
                openFileOutput("user_details", Context.MODE_PRIVATE).use { fos ->
                    fos.write(username.toByteArray())
                    fos.write(password.toByteArray())
                }
                Toast.makeText(applicationContext, "Details Saved Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DetailsActivity::class.java))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
