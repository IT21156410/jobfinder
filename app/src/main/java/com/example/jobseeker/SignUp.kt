package com.example.jobseeker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobseeker.databinding.ActivitySignUpBinding
import com.example.jobseeker.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        //last textview navigation
        binding.gotologinActivity.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        binding.registrer.setOnClickListener {
            print("Im in onclicklister")
            validateData()
        }
    }


    private fun validateData() {

        print("Im in validateData")
        val fullName = binding.fullName.text.toString()
        val email = binding.signInEmail.text.toString()
        val password = binding.password.text.toString()
        val confirmPass = binding.password.text.toString() //TODO: add confirm password to UI

        print("Data from signUp $fullName $email $password $confirmPass")


        if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            if (password == confirmPass) {
                print("passwords matched")
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Sign In", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

        }
    }


}