package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobseeker.databinding.ActivityUserProfileBinding
import com.example.jobseeker.model.UserModel
import com.example.jobseeker.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityUserProfileBinding
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth

        val userId = firebaseAuth.currentUser?.uid

        if (userId != null) {
            Config.showDialog(this)
            database.child("users").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(UserModel::class.java)
                        if (user != null) {
                            binding.email.setText(user.email)
                            binding.profileName.text = user.name
                            binding.name.setText(user.name)
                            binding.mobile.setText(user.mobile)
                            binding.location.setText(user.location)
                            binding.dob.setText(user.dob)
                        }
                        Config.hideDialog()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Config.hideDialog()
                        Toast.makeText(
                            this@UserProfile,
                            "Failed to retrieve user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        binding.saveProfile.setOnClickListener {
            if (validateData()) {
                updateData(userId!!)
            }
        }
    }


    private fun validateData(): Boolean {
        val userId = firebaseAuth.currentUser?.uid
        val email = binding.email.text.toString()
        val name = binding.name.text.toString()
        val mobile = binding.mobile.text.toString()
        val location = binding.location.text.toString()
        val dob = binding.dob.text.toString()


        if (userId == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isBlank() || name.isBlank() || mobile.isBlank() || location.isBlank() || dob.isBlank()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return false
        } else if (location.length > 30) {
            Toast.makeText(
                this,
                "Location cannot be longer than 30 characters",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    private fun updateData(userId: String?) {
        val user = UserModel(
            email = binding.email.text.toString(),
            name = binding.name.text.toString(),
            mobile = binding.mobile.text.toString(),
            location = binding.location.text.toString(),
            dob = binding.dob.text.toString(),
        )

        Config.showDialog(this)
        if (userId != null) {
            database.child("users").child(userId).setValue(user)
                .addOnSuccessListener {
                    Config.hideDialog()
                    Toast.makeText(this, "user updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, UserProfile::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
                }
        } else {
            Config.hideDialog()
            Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
        }
    }
}