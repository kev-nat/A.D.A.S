package com.example.kotlincoba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Home : AppCompatActivity() {
    fun getCount(lockerRef: DatabaseReference, onStatusValue: (String, String?) -> Unit) {
        lockerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val statusValue = dataSnapshot.value?.toString()

                val countValue = statusValue?.substringAfter("Count=")?.substringBefore("}").toString()
                val warning = statusValue?.substringAfter("Warning=")?.substringBefore(",").toString()


                if (statusValue != null) {
                    onStatusValue(countValue,warning)
                }

//
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("fail", "Failed to read value.", error.toException())

            }
        })
    }

    private lateinit var button: Button
    private lateinit var count: TextView
    private lateinit var textView: TextView
    private lateinit var Good: TextView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val auth = FirebaseAuth.getInstance()
        button = findViewById(R.id.logout)
        textView = findViewById(R.id.user_detail)
        Good = findViewById(R.id.good)
        count = findViewById(R.id.count)
        val user = auth.currentUser

        if (user == null) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            textView.text =  "User Email: " + user.email
        }

        progressBar = findViewById(R.id.progressBar)
        button.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Result")
        Log.d("jalan", "program jalan")

        getCount(myRef) { countValue, warning ->
            count.text = "Warning Count: " + countValue
            Good.text = warning

            val imageView1: ImageView = findViewById(R.id.imageView1)
            val imageView2: ImageView = findViewById(R.id.imageView2)

            if (warning == "Drive Safely") {
                imageView1.visibility = View.VISIBLE
            }
            else {
                imageView1.visibility = View.GONE
            }

            if (warning == "u're good") {
                imageView2.visibility = View.VISIBLE
            }
            else {
                imageView2.visibility = View.GONE
            }


        }
    }
}