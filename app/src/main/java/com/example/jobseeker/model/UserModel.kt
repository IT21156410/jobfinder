package com.example.jobseeker.model

data class UserModel(
    val userid: String? = "",
    val email: String? = "",
    val name: String? = "",
    val mobile: String? = "",
    val location: String? = "",
    val dob: String? = "",
    val image: String? = "",

    ) {

    constructor() : this("", "", "", "", "", "", "")


}
