    package com.example.kotlincoba

    import android.content.Intent
    import android.os.Bundle
    import android.text.TextUtils
    import android.view.View
    import android.widget.Button
    import android.widget.ProgressBar
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.android.material.textfield.TextInputEditText
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseAuthWeakPasswordException

    class LoginActivity : AppCompatActivity() {
        val mAuth = FirebaseAuth.getInstance()
        public override fun onStart() {
            super.onStart()
            val currentUser = mAuth!!.currentUser
            if (currentUser != null) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        private lateinit var buttonLogin: Button
        private lateinit var textView: TextView
        private lateinit var editTextEmail: TextInputEditText
        private lateinit var editTextPassword: TextInputEditText
        private lateinit var progressBar: ProgressBar

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail = findViewById(R.id.inputEmail)
        editTextPassword = findViewById(R.id.inputPassword)
        progressBar = findViewById(R.id.progressBar)
        buttonLogin = findViewById(R.id.btnlogin)
        textView = findViewById(R.id.textViewSignUp)

        textView.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
        buttonLogin.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this@LoginActivity, "Enter email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Enter password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(applicationContext, Home::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }
}