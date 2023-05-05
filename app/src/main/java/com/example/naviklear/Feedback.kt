package com.example.naviklear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Feedback : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)


        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        val submit = findViewById<Button>(R.id.Submit)
        val home = findViewById<ImageButton>(R.id.backButton)
        val editText = findViewById<EditText>(R.id.editText)

        submit.setOnClickListener{
                if(editText.text == null||editText.text.trim()==""){
                    Toast.makeText(this,"please input data, feedback cannot be blank", Toast.LENGTH_LONG).show()
                }
                else{
                    //val editText = editText.text.toString()

                    var feedbackmsg = editText.text.toString();
                    val data = hashMapOf(
                        "FeedbackFromUser" to feedbackmsg
                    )

                    db.collection("UserFeedback").add(data).addOnSuccessListener { documentReference ->
                        Log.d("MYDEBUG", "DcoumentSnapShot added")
                        Toast.makeText(this,"We received your feedback, Thank you!", Toast.LENGTH_LONG).show()
                    }
                        .addOnFailureListener{e ->
                            Log.w("MYDEBUG", "Error addiing document", e)
                        }


                }
                editText.setText(null)
            }

        home.setOnClickListener{
                val goToMainActivity = Intent(this@Feedback, MainActivity::class.java)
                startActivity(goToMainActivity)
                finish()
        }

    }
}