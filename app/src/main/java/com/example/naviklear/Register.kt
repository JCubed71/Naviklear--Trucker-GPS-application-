package com.example.naviklear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var first:EditText
    private lateinit var last:EditText
    private lateinit var company:EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        db = FirebaseFirestore.getInstance()

        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val signup: Button = findViewById(R.id.signup)

        first = findViewById(R.id.Fname)
        last = findViewById(R.id.Lname)
        email = findViewById(R.id.Email)
        password = findViewById(R.id.Pass)
        company = findViewById(R.id.CName)


        signup.setOnClickListener {
            val First = first.text.toString().trim()
            val Last = last.text.toString().trim()
            val Mail = email.text.toString().trim()
            val Password = password.text.toString().trim()
            val Company = company.text.toString().trim()

            if (First.isEmpty()) {
                first.error = "Please input your first name"
                return@setOnClickListener
            }
            else if (First.length<3){
                first.error = "Please input your first name with more than 3 letters"
                return@setOnClickListener
            }
            else if (First.length>30){
                first.error = "Please input your first name with less than 30 letters"
                return@setOnClickListener
            }
            else if (Last.isEmpty()) {
                last.error = "Please input your last name"
                return@setOnClickListener
            }
            else if (Mail.isEmpty()) {
                email.error = "Please input your email address"
                return@setOnClickListener
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(Mail).matches()){
                email.error = "Invalid Email"
                return@setOnClickListener
            }


            else if (Password.isEmpty()) {
                password.error = "Please input your Password"
                return@setOnClickListener
            }

            else if (Company.isEmpty()) {
                company.error = "Please input your Company"
                return@setOnClickListener
            }

            else {
                signUpUser()
                var UserInfoF = first.text.toString()
                var UserInfoL = last.text.toString()
                var UserInfoC = company.text.toString()
                var Email = email.text.toString()

                val datas = hashMapOf(
                    "First" to UserInfoF,
                    "Last" to UserInfoL,
                    "Email" to Email,
                    "Company" to UserInfoC
                )

                db.collection(Email).add(datas).addOnSuccessListener {
                    Log.d("MYDEBUGGER", "DocumentSnapShot added")

                }
                    .addOnFailureListener{e ->
                        Log.w("MYDEBUGGER", "Error adding info", e)
                    }




                }


            }
        }



    private fun signUpUser() {
        val email = email.text.toString()
        val pass = password.text.toString()


        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}



