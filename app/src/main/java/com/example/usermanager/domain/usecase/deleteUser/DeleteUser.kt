package com.example.usermanager.domain.usecase.deleteUser

import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DeleteUser {
    fun deleteUser(userId: Int) : Flow<Resource<Unit>>
}