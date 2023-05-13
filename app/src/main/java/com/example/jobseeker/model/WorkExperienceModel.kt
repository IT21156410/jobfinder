package com.example.jobseeker.model

data class WorkExperienceModel(
    val position: String? = "",
    val companyName: String? = "",
    val startDate: String? = "",
    val endDate: String? = "",
    val currentPosition: Boolean? = true
) {

    constructor() : this("", "", "", "", true)
}
