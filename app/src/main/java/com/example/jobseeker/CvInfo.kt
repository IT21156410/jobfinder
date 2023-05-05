package com.example.jobseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CvInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_info)

        val goBackButton = findViewById<Button>(R.id.goBack)

        goBackButton.setOnClickListener {
            finish()
        }
    }
}