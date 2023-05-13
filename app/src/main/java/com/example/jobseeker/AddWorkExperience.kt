package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.jobseeker.databinding.ActivityAddWorkExperienceBinding
import com.example.jobseeker.model.WorkExperienceModel
import com.example.jobseeker.utils.Config
import com.google.firebase.database.FirebaseDatabase

class AddWorkExperience : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkExperienceBinding
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkExperienceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveWorkExperience.setOnClickListener {
            if (validateData()) {
                storeData()
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
        val position = binding.position.text.toString()
        val companyName = binding.companyName.text.toString()
        val startDate = binding.startDate.text.toString()
        val endDate = binding.endDate.text.toString()
        val isCurrentPosition = binding.isCurrentPosition.isChecked


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
}


