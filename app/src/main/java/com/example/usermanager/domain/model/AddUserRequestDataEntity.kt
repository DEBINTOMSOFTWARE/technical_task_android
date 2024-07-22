package com.example.usermanager.domain.model

data class AddUserRequestDataEntity(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)
