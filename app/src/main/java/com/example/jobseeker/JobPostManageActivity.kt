package com.example.jobseeker

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.jobseeker.model.JobPostModel
import com.google.firebase.database.*

class JobPostManageActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_post_manage)

        database = FirebaseDatabase.getInstance().reference

        displayJobPosts()
    }

    private fun displayJobPosts() {

        val jobPostsRef = database.child("job_posts")
        val linearLayout = findViewById<LinearLayout>(R.id.jobPostManagerLinearLayout)

        jobPostsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                linearLayout.removeAllViews()

                for (jobPostSnapshot in snapshot.children) {
                    try {
                        val jobPost = jobPostSnapshot.getValue(JobPostModel::class.java)

                        val jobPostView = layoutInflater.inflate(R.layout.job_post_card, null)

                        jobPostView.findViewById<TextView>(R.id.textViewJobTitle).text = jobPost?.position
                        jobPostView.findViewById<TextView>(R.id.textViewCompanyName).text = jobPost?.companyName
                        jobPostView.findViewById<TextView>(R.id.textViewJobDescription).text = jobPost?.jobDescription

                        // Add margin to the bottom of each job post view
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin = resources.getDimensionPixelSize(R.dimen.job_post_card_margin_top)
                        jobPostView.layoutParams = params

                        linearLayout.addView(jobPostView)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to create job post view", e)
                        Toast.makeText(applicationContext, "Failed to create job post view", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to retrieve job posts", error.toException())
                Toast.makeText(applicationContext, "Failed to retrieve job posts", Toast.LENGTH_LONG).show()
            }
        })
    }


}
