package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class CvProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_profile)


        val gotToViewWorkExperienceButton = findViewById<ImageButton>(R.id.gotToViewWorkExperience)
        gotToViewWorkExperienceButton.setOnClickListener {
            val intent = Intent(this, ViewWorkExperience::class.java)
            // start your next activity
            startActivity(intent)
        }

        val addWorkExperienceButton = findViewById<ImageButton>(R.id.addWorkExperience)
        addWorkExperienceButton.setOnClickListener {
            val intent = Intent(this, AddWorkExperience::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}