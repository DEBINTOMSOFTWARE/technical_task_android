package com.example.usermanager.domain.usecase.getusers

import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetUsers {
    fun getUsers(page: Int): Flow<Resource<List<UserItemEntity>>>
}