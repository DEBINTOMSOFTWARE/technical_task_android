package com.example.usermanager.domain.usecase

import com.example.usermanager.TestConstants
import com.example.usermanager.addRequestData
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.domain.usecase.adduser.AddUser
import com.example.usermanager.domain.usecase.adduser.AddUserImpl
import com.example.usermanager.user
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddUserImplTest {

    private lateinit var repository: UsersRepository
    private lateinit var addUserUseCase: AddUser

    @Before
    fun setUp() {
        repository = mockk()
        addUserUseCase = AddUserImpl(repository)
    }

    @Test
    fun givenRepositoryReturnsUser_whenAddUserIsCalled_thenReturnUser() = runTest {
        val mockResponse: Flow<Resource<UserItemEntity>> = flow {
            emit(Resource.Success(user))
        }

        coEvery { repository.addUser(addRequestData) } returns mockResponse
        val result = addUserUseCase.addUser(addRequestData)
        result.collect {
            assert(it is Resource.Success)
            val user = (it as Resource.Success).data
            assert(user?.email == TestConstants.EMAIL)
        }
    }

    @Test
    fun givenRepositoryReturnsError_whenAddUserIsCalled_thenReturnError() = runTest {
        val mockResponse: Flow<Resource<UserItemEntity>> = flow {
            emit(Resource.Error(ErrorEntity.Network))
        }

        coEvery { repository.addUser(addRequestData) } returns mockResponse
        val result = addUserUseCase.addUser(addRequestData)
        result.collect {
            assert(it is Resource.Error)
            assert((it as Resource.Error).message == ErrorEntity.Network)
        }
    }
}