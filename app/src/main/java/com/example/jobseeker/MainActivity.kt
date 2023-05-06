package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToCvActivityButton = findViewById<Button>(R.id.gotoCvActivity)
        goToCvActivityButton.setOnClickListener {
            val intent = Intent(this, CvInfo::class.java)
            // start your next activity
            startActivity(intent)
        }

        val goToJobSearchActivityButton = findViewById<Button>(R.id.gotoJobSearch)
        goToJobSearchActivityButton.setOnClickListener {
            val intent = Intent(this, JobSearchActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
