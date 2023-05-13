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
                        val workExperience =
                            WorkExperienceSnapshot.getValue(WorkExperienceModel::class.java)

                        val workExperienceView =
                            layoutInflater.inflate(R.layout.work_experience_card, null)

                        workExperienceView.findViewById<TextView>(R.id.position).text =
                            workExperience?.position
                        workExperienceView.findViewById<TextView>(R.id.textViewCompanyName).text =
                            workExperience?.companyName
                        workExperienceView.findViewById<TextView>(R.id.startDate).text =
                            workExperience?.startDate
                        if (workExperience?.currentPosition == true) {
                            workExperienceView.findViewById<TextView>(R.id.endDate)
                                .setText(R.string.present)
                        } else {
                            workExperienceView.findViewById<TextView>(R.id.endDate).text =
                                workExperience?.endDate
                        }

                        // Add margin to the bottom of each experience view
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin =
                            resources.getDimensionPixelSize(R.dimen.work_experience_card_margin_top)
                        workExperienceView.layoutParams = params

                        linearLayout.addView(workExperienceView)

                        val experienceId = WorkExperienceSnapshot.key

                        // Set OnClickListener for edit button
                        val editButton =
                            workExperienceView.findViewById<Button>(R.id.buttonEditExperience)
                        editButton.setOnClickListener {
                            val intent =
                                Intent(this@ViewWorkExperience, AddWorkExperience::class.java)
                            intent.putExtra("experienceId", experienceId)
                            intent.putExtra("isEdit", true)
                            startActivity(intent)
                        }

                        // Set OnClickListener for delete button
                        val deleteButton =
                            workExperienceView.findViewById<Button>(R.id.buttonDeleteExperience)
                        deleteButton.setOnClickListener {
                            deleteExperience(experienceId!!)
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to create experience view", e)
                        Toast.makeText(
                            applicationContext,
                            "Failed to create experience view",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Config.hideDialog()
                Log.e(TAG, "Failed to retrieve experiences", error.toException())
                Toast.makeText(
                    applicationContext,
                    "Failed to retrieve experiences",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    private fun deleteExperience(experienceId: String) {
        val experienceRef = database.child("work_experience").child(experienceId)

        val confirmationDialog = AlertDialog.Builder(this)
            .setTitle("Delete Experience")
            .setMessage("Are you sure you want to delete this experience?")
            .setPositiveButton("Yes") { dialog, which ->
                experienceRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Experience deleted successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to delete experience", exception)
                        Toast.makeText(this, "Failed to delete experience", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .create()
        confirmationDialog.show()
    }

}