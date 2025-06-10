package com.sellproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment(R.layout.fragment_home) {

    // Hàm được gọi sau khi fragment đã "gắn" vào giao diện
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lấy thông tin người dùng hiện tại từ Firebase Authentication
        val currentUser = FirebaseAuth.getInstance().currentUser

        // Tìm các view trong layout bằng ID
        val txtWelcome = view.findViewById<TextView>(R.id.txtWelcome)
        val txtEmail = view.findViewById<TextView>(R.id.txtEmail)
        val txtUid = view.findViewById<TextView>(R.id.txtUid)
        val btnCreateFood = view.findViewById<Button>(R.id.btnCreateFood)

        // Ẩn nút thêm món ăn mặc định — chỉ hiển thị nếu là admin
        btnCreateFood.visibility = View.GONE

        // Nếu người dùng đã đăng nhập
        if (currentUser != null) {
            // Hiển thị email và UID của người dùng
            txtWelcome.text = "Xin chào, ${currentUser.email}!"
            txtEmail.text = "Email: ${currentUser.email}"
            txtUid.text = "UID: ${currentUser.uid}"

            // Lấy UID người dùng hiện tại
            val uid = currentUser.uid

            // Truy cập node "users/{uid}" trong Realtime Database để kiểm tra role
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

            // Lấy giá trị trường "role" từ database
            userRef.child("role").get().addOnSuccessListener { snapshot ->
                val role = snapshot.getValue(String::class.java)

                // Nếu role là "admin" → hiện nút thêm món ăn
                if (role == "admin") {
                    btnCreateFood.visibility = View.VISIBLE
                }
            }.addOnFailureListener {
                // Nếu xảy ra lỗi khi đọc dữ liệu
                Toast.makeText(requireContext(), "Lỗi khi kiểm tra quyền người dùng", Toast.LENGTH_SHORT).show()
            }

        } else {
            // Nếu chưa đăng nhập → hiển thị thông báo
            txtWelcome.text = "Không có người dùng đang đăng nhập"
        }

        // Sự kiện khi người dùng bấm nút "Thêm món ăn"
        btnCreateFood.setOnClickListener {
            // Mở màn hình thêm món ăn
            startActivity(Intent(requireContext(), createFoodActivity::class.java))
        }
    }
}
