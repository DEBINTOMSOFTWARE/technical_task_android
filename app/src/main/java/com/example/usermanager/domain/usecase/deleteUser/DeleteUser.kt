package com.example.usermanager.domain.usecase.deleteUser

import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteUser {
    fun deleteUser(userId: Int): Flow<Resource<Unit>>
}