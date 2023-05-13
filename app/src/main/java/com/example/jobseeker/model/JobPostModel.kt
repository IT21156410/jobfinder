package com.example.jobseeker.model

data class JobPostModel(
    val position: String? = "",
    val qualification: String? = "",
    val companyName: String? = "",
    val startDate: String? = "",
    val endDate: String? = "",
    val jobDescription: String? = "",
    val location: String? = ""
) {

    constructor() : this("", "", "", "", "", "", "")
}
