package com.sellproject

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sellproject.data.Food
import com.sellproject.data.Users

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val auth = FirebaseAuth.getInstance()

        val emailField = findViewById<EditText>(R.id.edtEmail)
        val passwordField = findViewById<EditText>(R.id.edtPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.edtConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val loginLink = findViewById<Button>(R.id.btnLogin)
        val database = FirebaseDatabase.getInstance().getReference("users")


        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        val uid = currentUser?.uid ?: ""
                        val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

                        val user = Users(
                            userId = uid,
                            role = "user"
                        )

                        userRef.setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Lưu thông tin người dùng thất bại!", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(this, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}