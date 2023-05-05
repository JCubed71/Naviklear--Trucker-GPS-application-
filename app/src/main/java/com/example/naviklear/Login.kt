package com.example.naviklear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var register: Button
    private lateinit var login: Button
    private lateinit var Email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Email = findViewById(R.id.user_input)
        password = findViewById(R.id.pass_input)
        login = findViewById(R.id.login)
        login.setOnClickListener {
            val user = Email.text.toString().trim()
            val pass = password.text.toString().trim()
            if (user.isEmpty()) {
                Email.error = "Please input your email"
                return@setOnClickListener
            }
            else if (pass.isEmpty()) {
                password.error = "Please input your password"
                return@setOnClickListener
            }
            else{
                login()

            }
        }
        register = findViewById(R.id.register)
        register.setOnClickListener {registerscreen()}
    }
    private fun login() {
        val email = Email.text.toString()
        val pass = password.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }


    private fun registerscreen(){
        val intent = Intent(this, Register::class.java)
        startActivity(intent)


    }
}