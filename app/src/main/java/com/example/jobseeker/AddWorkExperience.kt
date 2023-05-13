package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.jobseeker.databinding.ActivityAddWorkExperienceBinding
import com.example.jobseeker.model.WorkExperienceModel
import com.example.jobseeker.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class AddWorkExperience : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkExperienceBinding
    private val database = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkExperienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        val isEdit = intent.getBooleanExtra("isEdit", false)
        var experienceId: String? = null
        if (isEdit) {
            experienceId = intent.getStringExtra("experienceId")
            if (experienceId != null) {
                database.child("work_experience").child(experienceId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val experience = snapshot.getValue(WorkExperienceModel::class.java)
                            if (experience != null) {
                                binding.position.setText(experience.position)
                                binding.companyName.setText(experience.companyName)
                                binding.startDate.setText(experience.startDate)
                                binding.endDate.setText(experience.endDate)
                                binding.isCurrentPosition.isChecked =
                                    experience.currentPosition == true
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@AddWorkExperience,
                                "Failed to retrieve experience data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }

        binding.saveWorkExperience.setOnClickListener {
            if (validateData()) {
                if (isEdit) {
                    updateData(experienceId!!)
                } else {
                    storeData()
                }
            }
        }


        binding.isCurrentPosition.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // If the box is checked, hide the end date field and set its value to null
                binding.endDateLabel.visibility = View.GONE
                binding.endDate.visibility = View.GONE
                binding.endDate.text = null
            } else {
                // If the box is unchecked, show the end date field
                binding.endDateLabel.visibility = View.VISIBLE
                binding.endDate.visibility = View.VISIBLE
            }
        }

    }

    private fun validateData(): Boolean {
        val userId = firebaseAuth.currentUser?.uid
        val position = binding.position.text.toString()
        val companyName = binding.companyName.text.toString()
        val startDate = binding.startDate.text.toString()
        val endDate = binding.endDate.text.toString()
        val isCurrentPosition = binding.isCurrentPosition.isChecked


        if (userId == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (position.isBlank() || companyName.isBlank() || startDate.isBlank() || (!isCurrentPosition && endDate.isBlank())) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return false
        } else if (position.length > 20) {
            Toast.makeText(
                this,
                "Job position cannot be longer than 20 characters",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (companyName.length > 28) {
            Toast.makeText(
                this,
                "Job company name cannot be longer than 28 characters",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    private fun storeData() {
        val workExperience = WorkExperienceModel(
            userId = firebaseAuth.currentUser?.uid,
            position = binding.position.text.toString(),
            companyName = binding.companyName.text.toString(),
            startDate = binding.startDate.text.toString(),
            endDate = binding.endDate.text.toString(),
            currentPosition = binding.isCurrentPosition.isChecked,
        )

        Config.showDialog(this)
        val workExperienceId = database.child("work_experience").push().key
        if (workExperienceId != null) {
            database.child("work_experience").child(workExperienceId).setValue(workExperience)
                .addOnSuccessListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Work experience added successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, AddWorkExperience::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Failed to add work experience", Toast.LENGTH_SHORT).show()
                }
        } else {
            Config.hideDialog()
            Toast.makeText(this, "Failed to generate work experience id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(experienceId: String?) {
        val experience = WorkExperienceModel(
            userId = firebaseAuth.currentUser?.uid,
            position = binding.position.text.toString(),
            companyName = binding.companyName.text.toString(),
            startDate = binding.startDate.text.toString(),
            endDate = binding.endDate.text.toString(),
            currentPosition = binding.isCurrentPosition.isChecked,
        )
         
        Config.showDialog(this)
        if (experienceId != null) {
            database.child("work_experience").child(experienceId).setValue(experience)
                .addOnSuccessListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Experience updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, ViewWorkExperience::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Config.hideDialog()
                    Toast.makeText(this, "Failed to update experience", Toast.LENGTH_SHORT).show()
                }
        } else {
            Config.hideDialog()
            Toast.makeText(this, "Failed to update experience", Toast.LENGTH_SHORT).show()
        }
    }
}


