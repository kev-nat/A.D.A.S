package com.example.kotlincoba

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var button1: Button
    private lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button1.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                goToUrl()
                finish()
            }

            private fun goToUrl() {
                val uriUrl = Uri.parse("https://www.youtube.com/watch?v=hWvM6de6mG8")
                val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
                startActivity(launchBrowser)
            }
        })
    }
}