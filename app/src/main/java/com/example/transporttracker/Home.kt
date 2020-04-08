package com.example.transporttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home2.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        login.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email.text.toString(),password.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Succesfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    intent.flags=
                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
