package com.example.usermanager.domain.usecase

import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetUsers {
   fun getUsers(page: Int) : Flow<Resource<List<UserListItemEntity>>>
}