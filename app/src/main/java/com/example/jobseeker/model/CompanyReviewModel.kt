package com.example.jobseeker.model

data class CompanyReviewModel(
    val userId: String? = "",
    val nicName: String? = "",
    val title: String? = "",
    val review: String? = "",
) {
    constructor() : this("", "", "", "")
}
