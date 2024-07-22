package com.example.usermanager.domain.usecase.adduser

import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AddUser {
    fun addUser(userData: AddUserRequestDataEntity) : Flow<Resource<UserItemEntity>>
}