package com.example.jobseeker

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.jobseeker.databinding.ActivityCompanyReviewsBinding
import com.example.jobseeker.model.CompanyReviewModel
import com.example.jobseeker.utils.Config
import com.google.firebase.database.*

class CompanyReviews : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyReviewsBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val goToAddReviewPageButton = findViewById<Button>(R.id.goToAddReviewPage)
        goToAddReviewPageButton.setOnClickListener {
            val intent = Intent(this, AddReviews::class.java)
            // start your next activity
            startActivity(intent)
        }

        display()
    }

    private fun display() {
        Config.showDialog(this)

        val reviewRef = database.child("company_reviews")
        val linearLayout = findViewById<LinearLayout>(R.id.reviewLinearLayout)

        reviewRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                linearLayout.removeAllViews()

                for (reviewSnapshot in snapshot.children) {
                    try {
                        val companyReview = reviewSnapshot.getValue(CompanyReviewModel::class.java)

                        val reviewView = layoutInflater.inflate(R.layout.company_review_card, null)

                        reviewView.findViewById<TextView>(R.id.nicName).text =
                            companyReview?.nicName
                        reviewView.findViewById<TextView>(R.id.title).text = companyReview?.title
                        reviewView.findViewById<TextView>(R.id.review).text = companyReview?.review

                        // Add margin to the bottom of each review view
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin =
                            resources.getDimensionPixelSize(R.dimen.card_margin_top)
                        reviewView.layoutParams = params

                        // Set OnClickListener for delete button
                        val deleteButton = reviewView.findViewById<Button>(R.id.buttonDeleteReview)
                        val reviewId = reviewSnapshot.key
                        deleteButton.setOnClickListener {
                            deleteJobPost(reviewId!!)
                        }

                        linearLayout.addView(reviewView)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to create review view", e)
                        Toast.makeText(
                            applicationContext,
                            "Failed to create review view",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Config.hideDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to retrieve reviews", error.toException())
                Toast.makeText(
                    applicationContext,
                    "Failed to retrieve reviews",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    private fun deleteJobPost(jobPostId: String) {
        val jobPostRef = database.child("company_reviews").child(jobPostId)

        val confirmationDialog = AlertDialog.Builder(this)
            .setTitle("Delete Review")
            .setMessage("Are you sure you want to delete this review?")
            .setPositiveButton("Yes") { dialog, which ->
                jobPostRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Job post deleted successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to delete review", exception)
                        Toast.makeText(this, "Failed to delete review", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .create()
        confirmationDialog.show()
    }

}