package com.example.usermanager.domain.usecase.deleteUser

import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserImpl @Inject constructor(private val usersRepository: UsersRepository) :
    DeleteUser {
    override fun deleteUser(userId: Int): Flow<Resource<Unit>> =
        usersRepository.deleteUser(userId)
}