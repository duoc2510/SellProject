package com.sellproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityLauncher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 👉 Chỉ cần sửa dòng dưới để test Activity khác:
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
