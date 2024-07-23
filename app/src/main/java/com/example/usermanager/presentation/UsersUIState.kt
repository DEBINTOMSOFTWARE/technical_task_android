package com.example.usermanager.presentation

import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.utils.ErrorEntity

data class UsersUIState(
    val users: List<UserItemEntity> = emptyList(),
    val user: UserItemEntity? =  null,
    val isLoading: Boolean = false,
    val error: ErrorEntity? = null,
    val exit: Boolean = false
)