package com.example.jobseeker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = Firebase.auth

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
        val addWorkExperienceButton = findViewById<Button>(R.id.addWorkExperience)
        addWorkExperienceButton.setOnClickListener {
            val intent = Intent(this, AddWorkExperience::class.java)
            // start your next activity
            startActivity(intent)
        }
        val goToProfileButton = findViewById<Button>(R.id.goToProfile)
        goToProfileButton.setOnClickListener {
            val intent = Intent(this, UserProfile::class.java)
            // start your next activity
            startActivity(intent)
        }
        val signOutButton = findViewById<Button>(R.id.signOut)
        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
