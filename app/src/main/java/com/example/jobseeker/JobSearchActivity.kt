package com.example.jobseeker

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.jobseeker.databinding.ActivityJobSearchBinding
import com.example.jobseeker.model.JobPostModel
import com.google.firebase.database.*
import java.util.*
import com.example.jobseeker.utils.Config

class JobSearchActivity : AppCompatActivity() {
    private lateinit var searchTermEditText: EditText
    private lateinit var locationEditText: EditText

    private lateinit var binding: ActivityJobSearchBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchTermEditText = findViewById(R.id.jobSearchTermEditText)
        locationEditText = findViewById(R.id.jobLocationEditText)
        val goBackButton = findViewById<Button>(R.id.goBackJobSearch)
        val jobSearchButton = findViewById<Button>(R.id.jobPostSsearchButton)

        database = FirebaseDatabase.getInstance().reference

        goBackButton.setOnClickListener {
            finish()
        }

        jobSearchButton.setOnClickListener {
            searchJobs()
        }
    }

    private fun searchJobs() {
        Config.showDialog(this)

        val searchTerm = searchTermEditText.text.toString().toLowerCase(Locale.ROOT)

        val jobPostsRef = database.child("job_posts")
        val linearLayout = findViewById<LinearLayout>(R.id.jobPostSearchLinearLayout)

        jobPostsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                linearLayout.removeAllViews()

                for (jobPostSnapshot in snapshot.children) {
                    try {
                        val jobPost = jobPostSnapshot.getValue(JobPostModel::class.java)

                        if (jobPost != null && isMatched(jobPost, searchTerm)) {

                            val jobPostView = layoutInflater.inflate(R.layout.job_post_card_show, null)

                            jobPostView.findViewById<TextView>(R.id.textViewJobTitle).text = jobPost.position
                            jobPostView.findViewById<TextView>(R.id.textViewCompanyName).text = jobPost.companyName
                            jobPostView.findViewById<TextView>(R.id.textViewJobDescription).text = jobPost.jobDescription

                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            params.bottomMargin = resources.getDimensionPixelSize(R.dimen.job_post_card_margin_top)
                            jobPostView.layoutParams = params

                            linearLayout.addView(jobPostView)
                        }
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "Failed to create job post view", e)
                        Toast.makeText(applicationContext, "Failed to create job post view", Toast.LENGTH_LONG).show()
                    }
                }
                Config.hideDialog()

                if (linearLayout.childCount == 0) {
                    Toast.makeText(applicationContext, "No job posts found matching the search criteria", Toast.LENGTH_LONG).show()
                }
                // remove the listener after it has finished its job
                jobPostsRef.removeEventListener(this)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Failed to retrieve job posts", error.toException())
                Toast.makeText(applicationContext, "Failed to retrieve job posts", Toast.LENGTH_LONG).show()
                // remove the listener after it has finished its job
                jobPostsRef.removeEventListener(this)
            }
        })
    }

    private fun isMatched(jobPost: JobPostModel, searchTerm: String): Boolean {
        return jobPost.position?.toLowerCase(Locale.ROOT)?.contains(searchTerm) == true ||
                jobPost.companyName?.toLowerCase(Locale.ROOT)?.contains(searchTerm) == true ||
                jobPost.jobDescription?.toLowerCase(Locale.ROOT)?.contains(searchTerm) == true ||
                jobPost.location?.toLowerCase(Locale.ROOT)?.contains(searchTerm) == true
    }
}
