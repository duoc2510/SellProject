package com.sellproject.InternalStorage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import com.sellproject.R


class DetailsActivity : AppCompatActivity() {
    var fstream: FileInputStream? = null
    var intent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details2)
        val result = findViewById<View?>(R.id.resultView) as TextView
        val back = findViewById<View?>(R.id.btnBack) as Button
        try {
            fstream = openFileInput("user_details")
            val sbuffer = StringBuffer()
            var i: Int
            while ((fstream!!.read().also { i = it }) != -1) {
                sbuffer.append(i.toChar())
            }
            fstream!!.close()
            val details: Array<String?>? =
                sbuffer.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            result.setText("Name: " + details!![0] + "\nPassword: " + details[1])
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        back.setOnClickListener(object : View.OnClickListener {
            public override fun onClick(v: View?) {
                intent = Intent(this@DetailsActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
