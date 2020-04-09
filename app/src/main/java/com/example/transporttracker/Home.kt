package com.example.transporttracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_home2.email
import kotlinx.android.synthetic.main.activity_home2.password

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        registration.setOnClickListener {
            val intent = Intent(this,signin::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {

        }
    }
    private fun logintofirebase(){
        val etemail: EditText = findViewById(R.id.email)
        val etpassword:EditText= findViewById(R.id.password)
        val email=email.text.toString()
        val password= password.text.toString()
        if (email.isEmpty()){
            etemail.error="Required"
        }else if(password.isEmpty()){
            etpassword.error="Required"
        }else {

           FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
               .addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                       val intent = Intent(this, MainActivity::class.java)
                       intent.flags =
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
