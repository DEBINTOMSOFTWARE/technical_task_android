package com.example.usermanager.domain.usecase

import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUsers {
    override fun getUsers(page: Int): Flow<Resource<List<UserListItemEntity>>> =
        usersRepository.getUsers(page)

}