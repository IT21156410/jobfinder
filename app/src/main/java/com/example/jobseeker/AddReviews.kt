package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobseeker.databinding.ActivityAddReviewsBinding
import com.example.jobseeker.model.CompanyReviewModel
import com.example.jobseeker.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class AddReviews : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewsBinding
    private val database = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.submitReview.setOnClickListener {
            if (validateData()) {
                storeData()
            }
        }

        val isEdit = intent.getBooleanExtra("isEdit", false)
        var reviewId: String? = null
        if (isEdit) {
            reviewId = intent.getStringExtra("reviewId")
            if (reviewId != null) {
                database.child("company_reviews").child(reviewId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val review = snapshot.getValue(CompanyReviewModel::class.java)
                            if (review != null) {
                                binding.nicName.setText(review.nicName)
                                binding.title.setText(review.title)
                                binding.review.setText(review.review)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@AddReviews,
                                "Failed to retrieve review data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        binding.submitReview.setOnClickListener {
            if (validateData()) {
                if (isEdit) {
                    updateData(reviewId!!)
                } else {
                    storeData()
                }
            }
        }
    }

    private fun validateData(): Boolean {
        val userId = firebaseAuth.currentUser?.uid
        val nicName = binding.nicName.text.toString()
        val title = binding.title.text.toString()
        val review = binding.review.text.toString()

        if (userId == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (nicName.isBlank() || title.isBlank() || review.isBlank()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return false
        } else if (title.length > 20) {
            Toast.makeText(
                this,
                "Review title cannot be longer than 20 characters",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (review.length > 60) {
            Toast.makeText(
                this,
                "Review cannot be longer than 60 characters",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    private fun storeData() {
        val workExperience = CompanyReviewModel(
            userId = firebaseAuth.currentUser?.uid,
            nicName = binding.nicName.text.toString(),
            title = binding.title.text.toString(),
            review = binding.review.text.toString(),
        )

        Config.showDialog(this)
        val workExperienceId = database.child("company_reviews").push().key
        if (workExperienceId != null) {
            database.child("company_reviews").child(workExperienceId).setValue(workExperience)
                .addOnSuccessListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, AddReviews::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Failed to add review", Toast.LENGTH_SHORT).show()
                }
        } else {
            Config.hideDialog()
            Toast.makeText(this, "Failed to generate review id", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateData(reviewId: String?) {
        val review = CompanyReviewModel(
            userId = firebaseAuth.currentUser?.uid,
            nicName = binding.nicName.text.toString(),
            title = binding.title.text.toString(),
            review = binding.review.text.toString(),
        )

        Config.showDialog(this)
        if (reviewId != null) {
            database.child("company_reviews").child(reviewId).setValue(review)
                .addOnSuccessListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Review updated successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CompanyReviews::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Failed to update review", Toast.LENGTH_SHORT).show()
                }
        } else {
            Config.hideDialog()
            Toast.makeText(this, "Failed to update review", Toast.LENGTH_SHORT).show()
        }
    }
}