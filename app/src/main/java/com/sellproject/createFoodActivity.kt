package com.sellproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sellproject.data.Food

class createFoodActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        database = FirebaseDatabase.getInstance().getReference("foods")
        val auth = FirebaseAuth.getInstance()

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtDescription = findViewById<EditText>(R.id.edtDescription)
        val edtPrice = findViewById<EditText>(R.id.edtPrice)
        val btnAdd = findViewById<Button>(R.id.btnAddFood)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnAdd.setOnClickListener {
            val id = database.push().key ?: return@setOnClickListener
            val name = edtName.text.toString()
            val desc = edtDescription.text.toString()
            val price = edtPrice.text.toString().toIntOrNull() ?: 0
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val uidUser = currentUser?.uid ?: "Không có"
            val food = Food(id, name, desc, price, uidUser)
            database.child(id).setValue(food)
                .addOnSuccessListener {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
        }
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}