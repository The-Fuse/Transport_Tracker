package com.example.transporttracker

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
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
