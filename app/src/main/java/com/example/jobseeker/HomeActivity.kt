package com.example.jobseeker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.jobseeker.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gotoCvActivity,
                        R.id.goToCvProfile,
                        R.id.gotoJobSearch,
                        R.id.addEducationBtn,
                        R.id.addJobPostBtn,
                        R.id.manageJobPostBtn,
                        R.id.goToCompanyList,
                        R.id.goToCompanyInfo,
                        R.id.gotToCompanyReviews,
                        R.id.goToSalary,
                        R.id.goToForgotPassword,
                        R.id.otpVerifyBtn,
                        R.id.resetPassword,
                        R.id.goToProfile,
                        R.id.signOut,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        val goToCvActivityButton = findViewById<Button>(R.id.gotoCvActivity)
//        goToCvActivityButton.setOnClickListener {
//            val intent = Intent(this, CvInfo::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val goToCvProfileButton = findViewById<Button>(R.id.goToCvProfile)
//        goToCvProfileButton.setOnClickListener {
//            val intent = Intent(this, CvProfile::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//
//        val goToJobSearchActivityButton = findViewById<Button>(R.id.gotoJobSearch)
//        goToJobSearchActivityButton.setOnClickListener {
//            val intent = Intent(this, JobSearchActivity::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//
//        val addEducationBtnButton = findViewById<Button>(R.id.addEducationBtn)
//        addEducationBtnButton.setOnClickListener {
//            val intent = Intent(this, AddEducation::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//
//        val addJobPostBtnButton = findViewById<Button>(R.id.addJobPostBtn)
//        addJobPostBtnButton.setOnClickListener {
//            val intent = Intent(this, JobPostAddActivity::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//
//        val manageJobPostBtnButton = findViewById<Button>(R.id.manageJobPostBtn)
//        manageJobPostBtnButton.setOnClickListener {
//            val intent = Intent(this, JobPostManageActivity::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//
//        val goToCompanyListButton = findViewById<Button>(R.id.goToCompanyList)
//        goToCompanyListButton.setOnClickListener {
//            val intent = Intent(this, CompanyList::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val goToCompanyInfoButton = findViewById<Button>(R.id.goToCompanyInfo)
//        goToCompanyInfoButton.setOnClickListener {
//            val intent = Intent(this, CompanyInfo::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val gotToCompanyReviewsButton = findViewById<Button>(R.id.gotToCompanyReviews)
//        gotToCompanyReviewsButton.setOnClickListener {
//            val intent = Intent(this, CompanyReviews::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val goToSalaryButton = findViewById<Button>(R.id.goToSalary)
//        goToSalaryButton.setOnClickListener {
//            val intent = Intent(this, Salary::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val goToForgotPasswordButton = findViewById<Button>(R.id.goToForgotPassword)
//        goToForgotPasswordButton.setOnClickListener {
//            val intent = Intent(this, ForgotPassword::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val otpVerifyBtnButton = findViewById<Button>(R.id.otpVerifyBtn)
//        otpVerifyBtnButton.setOnClickListener {
//            val intent = Intent(this, OTPVerification::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val resetPasswordButton = findViewById<Button>(R.id.resetPassword)
//        resetPasswordButton.setOnClickListener {
//            val intent = Intent(this, ResetPassword::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val goToProfileButton = findViewById<Button>(R.id.goToProfile)
//        goToProfileButton.setOnClickListener {
//            val intent = Intent(this, UserProfile::class.java)
//            // start your next activity
//            startActivity(intent)
//        }
//        val signOutButton = findViewById<Button>(R.id.signOut)
//        signOutButton.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            val intent = Intent(this, SignIn::class.java)
//            startActivity(intent)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onStart() {
//        super.onStart()
//
//        if (firebaseAuth.currentUser == null) {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//    }
}
