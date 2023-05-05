package com.example.naviklear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Profile : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var firstN:TextView
    private lateinit var lastN:TextView
    private lateinit var compN:TextView
    private lateinit var emailN:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        db = FirebaseFirestore.getInstance()
        firstN = findViewById(R.id.firstName)
        lastN = findViewById(R.id.lastName)
        compN = findViewById(R.id.companyName)
        emailN = findViewById(R.id.userEmail)
        auth = FirebaseAuth.getInstance()


        val backButton = findViewById<ImageButton>(R.id.backButton)

        val first = firstN.text.toString()
        val last = lastN.text.toString()
        val company = compN.text.toString()
        val email = emailN.text.toString()


        db.collection(auth.currentUser?.email.toString()).whereEqualTo("Email",auth.currentUser?.email.toString() ).get().addOnSuccessListener { result ->
            for (document in result) {
                val fName = document.get("First").toString()
                firstN.text = fName

                val lName = document.get("Last").toString()
                lastN.text = lName

                val cName = document.get("Company").toString()
                compN.text = cName

                val eName = document.get("Email").toString()
                emailN.text = eName

            


            }
        }.addOnFailureListener { exception ->
            Log.w("MYDEBUG", "Error getting documents.", exception)
        }





        backButton.setOnClickListener(){
            val goToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(goToMainActivity)
            finish()
        }


    }
}