package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CompanyReviews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_reviews)


        val goToAddReviewPageButton = findViewById<Button>(R.id.goToAddReviewPage)
        goToAddReviewPageButton.setOnClickListener {
            val intent = Intent(this, AddReviews::class.java)
            // start your next activity
            startActivity(intent)
        }

    }
}