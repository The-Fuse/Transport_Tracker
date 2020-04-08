package com.example.transporttracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signin.*

class signin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val auth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            performregister()
        }
    }
    private fun performregister(){
        val email=email.text.toString()
        val password= password.text.toString()
        val confirmpassword=confpassword.text.toString()

        if(password===confirmpassword){
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"PLease fill up the credentials!",Toast.LENGTH_SHORT).show()
                return
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(!it.isSuccessful){
                        Toast.makeText(this,"Please try again",Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }else{
                        Log.d("Main","Account created succesfully!!")
                    }
                    Toast.makeText(this,"Account created successfully!!",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,Home::class.java)
                    intent.flags=
                        Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
        }else{
            Toast.makeText(this,"Password don't matches",Toast.LENGTH_SHORT).show()
        }
    }
}
