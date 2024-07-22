package com.example.usermanager.presentation.intent

sealed class UserIntent {
    data object LoadUsers : UserIntent()
    data object exitUser: UserIntent()
}