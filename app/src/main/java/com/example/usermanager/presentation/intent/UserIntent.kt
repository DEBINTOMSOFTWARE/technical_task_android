package com.example.usermanager.presentation.intent

sealed class UserIntent {
    data object LoadUsers : UserIntent()
    data class AddUser(
        val name: String,
        val email: String,
        val gender: String,
        val status: String
    ) : UserIntent()

    data class DeleteUser(
        val userId: Int
    ) : UserIntent()

    data object ExitUser : UserIntent()
}