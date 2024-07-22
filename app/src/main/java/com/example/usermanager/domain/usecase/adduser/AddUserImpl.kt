package com.example.usermanager.domain.usecase.adduser

import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserImpl @Inject constructor(private val usersRepository: UsersRepository) : AddUser {
    override fun addUser(userData: AddUserRequestDataEntity): Flow<Resource<UserItemEntity>> =
        usersRepository.addUser(userData)
}