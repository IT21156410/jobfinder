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
import com.example.jobseeker.model.JobPostModel
import com.google.firebase.database.*
import androidx.appcompat.app.AlertDialog
import com.example.jobseeker.databinding.ActivityJobPostManageBinding
import com.example.jobseeker.utils.Config

class JobPostManageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobPostManageBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobPostManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        displayJobPosts()

//        binding.buttonEditJobPost.setOnClickListener {
//            val intent = Intent(this, JobPostAddActivity::class.java)
//            intent.putExtra("jobPostId", jobPostId)
//            intent.putExtra("isEdit", true)
//            startActivity(intent)
//        }
    }

    private fun displayJobPosts() {
        Config.showDialog(this)

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

                        val jobId = jobPostSnapshot.key

                        // Set OnClickListener for edit button
                        val editButton = jobPostView.findViewById<Button>(R.id.buttonEditJobPost)
                        editButton.setOnClickListener {
                            val intent = Intent(this@JobPostManageActivity, JobPostAddActivity::class.java)
                            intent.putExtra("jobPostId", jobId)
                            intent.putExtra("isEdit", true)
                            startActivity(intent)
                        }

                        // Set OnClickListener for delete button
                        val deleteButton = jobPostView.findViewById<Button>(R.id.buttonDeleteJobPost)
                        deleteButton.setOnClickListener {
                            deleteJobPost(jobId!!)
                        }

                        linearLayout.addView(jobPostView)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to create job post view", e)
                        Toast.makeText(applicationContext, "Failed to create job post view", Toast.LENGTH_LONG).show()
                    }
                }
                Config.hideDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to retrieve job posts", error.toException())
                Toast.makeText(applicationContext, "Failed to retrieve job posts", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun deleteJobPost(jobPostId: String) {
        val jobPostRef = database.child("job_posts").child(jobPostId)

        val confirmationDialog = AlertDialog.Builder(this)
            .setTitle("Delete Job Post")
            .setMessage("Are you sure you want to delete this job post?")
            .setPositiveButton("Yes") { dialog, which ->
                jobPostRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Job post deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to delete job post", exception)
                        Toast.makeText(this, "Failed to delete job post", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .create()
        confirmationDialog.show()
    }


}
