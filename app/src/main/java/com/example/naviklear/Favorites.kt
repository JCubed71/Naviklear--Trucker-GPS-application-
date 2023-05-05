package com.example.naviklear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Favorites : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var place1:TextView
    private lateinit var place2:EditText
    private lateinit var place3:EditText
    private lateinit var place4:EditText
    private lateinit var place5:EditText

    private lateinit var address1:EditText
    private lateinit var address2:EditText
    private lateinit var address3:EditText
    private lateinit var address4:EditText
    private lateinit var address5:EditText

    private lateinit var save1:Button
    private lateinit var save2:Button
    private lateinit var save3:Button
    private lateinit var save4:Button
    private lateinit var save5:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        place1 = findViewById(R.id.Place1)
        place2 = findViewById(R.id.Place2)
        place3 = findViewById(R.id.Place3)
        place4 = findViewById(R.id.Place4)
        place5 = findViewById(R.id.Place5)

        address1 = findViewById(R.id.Address1)
        address2 = findViewById(R.id.Address2)
        address3 = findViewById(R.id.Address3)
        address4 = findViewById(R.id.Address4)
        address5 = findViewById(R.id.Address5)

        save1 = findViewById(R.id.save1)
        save2 = findViewById(R.id.save2)
        save3 = findViewById(R.id.save3)
        save4 = findViewById(R.id.save4)
        save5 = findViewById(R.id.save5)

        save1.setOnClickListener(){
            var Place1DB = place1.text.toString()
            var Adress1DB =address1.text.toString()
            val data = hashMapOf(
                "place1" to Place1DB,
                "address1" to Adress1DB
            )
            db.collection(auth.currentUser?.email.toString()).add(data).addOnSuccessListener {
                Log.d("MYDEBUGGER", "DocumentSnapShot added")
                Toast.makeText(this, "The address has been saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{e ->
                    Log.w("MYDEBUGGER", "Error adding info", e)
                }
        }

        save2.setOnClickListener(){
            var Place2DB = place2.text.toString()
            var Adress2DB =address2.text.toString()
            val data = hashMapOf(
                "place2" to Place2DB,
                "address2" to Adress2DB
            )
            db.collection(auth.currentUser?.email.toString()).add(data).addOnSuccessListener {
                Log.d("MYDEBUGGER", "DocumentSnapShot added")
                Toast.makeText(this, "The address has been saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{e ->
                    Log.w("MYDEBUGGER", "Error adding info", e)
                }
        }

        save3.setOnClickListener(){
            var Place3DB = place3.text.toString()
            var Adress3DB =address3.text.toString()
            val data = hashMapOf(
                "place3" to Place3DB,
                "address3" to Adress3DB
            )
            db.collection(auth.currentUser?.email.toString()).add(data).addOnSuccessListener {
                Log.d("MYDEBUGGER", "DocumentSnapShot added")
                Toast.makeText(this, "The address has been saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{e ->
                    Log.w("MYDEBUGGER", "Error adding info", e)
                }
        }

        save4.setOnClickListener(){
            var Place4DB = place4.text.toString()
            var Adress4DB =address4.text.toString()
            val data = hashMapOf(
                "place4" to Place4DB,
                "address4" to Adress4DB
            )
            db.collection(auth.currentUser?.email.toString()).add(data).addOnSuccessListener {
                Log.d("MYDEBUGGER", "DocumentSnapShot added")
                Toast.makeText(this, "The address has been saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{e ->
                    Log.w("MYDEBUGGER", "Error adding info", e)
                }
        }

        save5.setOnClickListener(){
            var Place5DB = place5.text.toString()
            var Adress5DB =address5.text.toString()
            val data = hashMapOf(
                "place5" to Place5DB,
                "address5" to Adress5DB
            )
            db.collection(auth.currentUser?.email.toString()).add(data).addOnSuccessListener {
                Log.d("MYDEBUGGER", "DocumentSnapShot added")
                Toast.makeText(this, "The address has been saved", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{e ->
                    Log.w("MYDEBUGGER", "Error adding info", e)
                }
        }
        backButton.setOnClickListener(){
            val goToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(goToMainActivity)
            finish()
        }
    }
}