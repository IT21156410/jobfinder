package com.example.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class JobSearchActivity : AppCompatActivity() {

    private lateinit var jobTitleEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_search)

        val goBackButton = findViewById<Button>(R.id.goBackFrmJob)

        goBackButton.setOnClickListener {
            finish()
        }

        // Initialize views
        jobTitleEditText = findViewById(R.id.job_title_edittext)
        locationEditText = findViewById(R.id.location_edittext)
        searchButton = findViewById(R.id.search_button)

        // Set up click listener for search button
        searchButton.setOnClickListener {
            val jobTitle = jobTitleEditText.text.toString()
            val location = locationEditText.text.toString()
            // TODO: Implement job search functionality
        }
    }
}
