package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
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
         val goToCvProfileButton = findViewById<Button>(R.id.goToCvProfile)
        goToCvProfileButton.setOnClickListener {
            val intent = Intent(this, CvProfile::class.java)
            // start your next activity
            startActivity(intent)
        }

        val goToJobSearchActivityButton = findViewById<Button>(R.id.gotoJobSearch)
        goToJobSearchActivityButton.setOnClickListener {
            val intent = Intent(this, JobSearchActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        val addEducationBtnButton = findViewById<Button>(R.id.addEducationBtn)
        addEducationBtnButton.setOnClickListener {
            val intent = Intent(this, AddEducation::class.java)
            // start your next activity
            startActivity(intent)
        }

        val addJobPostBtnButton = findViewById<Button>(R.id.addJobPostBtn)
        addJobPostBtnButton.setOnClickListener {
            val intent = Intent(this, AddJobPostActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        val manageJobPostBtnButton = findViewById<Button>(R.id.manageJobPostBtn)
        manageJobPostBtnButton.setOnClickListener {
            val intent = Intent(this, JobPostManageActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        val goToCompanyListButton = findViewById<Button>(R.id.goToCompanyList)
        goToCompanyListButton.setOnClickListener {
            val intent = Intent(this, CompanyList::class.java)
            // start your next activity
            startActivity(intent)
        }
        val goToCompanyInfoButton = findViewById<Button>(R.id.goToCompanyInfo)
        goToCompanyInfoButton.setOnClickListener {
            val intent = Intent(this, CompanyInfo::class.java)
            // start your next activity
            startActivity(intent)
        }
        val gotToCompanyReviewsButton = findViewById<Button>(R.id.gotToCompanyReviews)
        gotToCompanyReviewsButton.setOnClickListener {
            val intent = Intent(this, CompanyReviews::class.java)
            // start your next activity
            startActivity(intent)
        }
        val goToSalaryButton = findViewById<Button>(R.id.goToSalary)
        goToSalaryButton.setOnClickListener {
            val intent = Intent(this, Salary::class.java)
            // start your next activity
            startActivity(intent)
        }
        val goToForgotPasswordButton = findViewById<Button>(R.id.goToForgotPassword)
        goToForgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            // start your next activity
            startActivity(intent)
        }
        val otpVerifyBtnButton = findViewById<Button>(R.id.otpVerifyBtn)
        otpVerifyBtnButton.setOnClickListener {
            val intent = Intent(this, OTPVerification::class.java)
            // start your next activity
            startActivity(intent)
        }
        val resetPasswordButton = findViewById<Button>(R.id.resetPassword)
        resetPasswordButton.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
