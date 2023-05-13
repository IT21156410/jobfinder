package com.example.jobseeker

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.jobseeker.model.WorkExperienceModel
import com.example.jobseeker.utils.Config
import com.google.firebase.database.*

class ViewWorkExperience : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_work_experience)

        database = FirebaseDatabase.getInstance().reference

        displayExperience()
    }

    private fun displayExperience() {

        Config.showDialog(this)
        val workExperienceRef = database.child("work_experience")
        val linearLayout = findViewById<LinearLayout>(R.id.WorkExperienceLinearLayout)

        workExperienceRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                linearLayout.removeAllViews()

                Config.hideDialog()
                for (WorkExperienceSnapshot in snapshot.children) {
                    try {
                        val workExperience = WorkExperienceSnapshot.getValue(WorkExperienceModel::class.java)

                        val workExperienceView = layoutInflater.inflate(R.layout.work_experience_card, null)

                        workExperienceView.findViewById<TextView>(R.id.textViewJobTitle).text = workExperience?.position
                        workExperienceView.findViewById<TextView>(R.id.textViewCompanyName).text = workExperience?.companyName
                        workExperienceView.findViewById<TextView>(R.id.startDate).text = workExperience?.startDate
                        if (workExperience?.currentPosition == true){
                            workExperienceView.findViewById<TextView>(R.id.endDate).setText(R.string.present)
                        }else{
                            workExperienceView.findViewById<TextView>(R.id.endDate).text = workExperience?.endDate
                        }

                        // Add margin to the bottom of each job post view
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin = resources.getDimensionPixelSize(R.dimen.work_experience_card_margin_top)
                        workExperienceView.layoutParams = params

                        linearLayout.addView(workExperienceView)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to create job post view", e)
                        Toast.makeText(applicationContext, "Failed to create job post view", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Config.hideDialog()
                Log.e(TAG, "Failed to retrieve job posts", error.toException())
                Toast.makeText(applicationContext, "Failed to retrieve job posts", Toast.LENGTH_LONG).show()
            }
        })
    }
}