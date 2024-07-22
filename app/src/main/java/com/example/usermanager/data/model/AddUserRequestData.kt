package com.example.usermanager.data.model

data class AddUserRequestData(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)
