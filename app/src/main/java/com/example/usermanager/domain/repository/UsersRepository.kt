package com.example.usermanager.domain.repository

import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UsersRepository {
    fun getUsers(page: Int): Flow<Resource<List<UserItemEntity>>>
    fun addUser(userData: AddUserRequestDataEntity) : Flow<Resource<UserItemEntity>>
    fun deleteUser(userId: Int): Flow<Resource<Unit>>
}