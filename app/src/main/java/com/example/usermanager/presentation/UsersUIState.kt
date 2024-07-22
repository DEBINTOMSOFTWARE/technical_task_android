package com.example.usermanager.presentation

import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.utils.ErrorEntity

data class UsersUIState(
    val users: List<UserListItemEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorEntity? = null,
    val exit: Boolean = false
)