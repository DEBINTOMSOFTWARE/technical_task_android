package com.example.usermanager.domain.repository

import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(page: Int): Flow<Resource<List<UserListItemEntity>>>
}