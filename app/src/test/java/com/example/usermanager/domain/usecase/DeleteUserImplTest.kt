package com.example.usermanager.domain.usecase

import com.example.usermanager.TestConstants
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.domain.usecase.deleteUser.DeleteUser
import com.example.usermanager.domain.usecase.deleteUser.DeleteUserImpl
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteUserImplTest {

    private lateinit var repository: UsersRepository
    private lateinit var deleteUserUseCase: DeleteUser

    @Before
    fun setUp() {
        repository = mockk()
        deleteUserUseCase = DeleteUserImpl(repository)
    }

    @Test
    fun givenRepositoryReturnsData_whenDeleteUserIsCalled_thenReturnData() = runTest {
        val mockResponse: Flow<Resource<Unit>> = flow {
            emit(Resource.Success(Unit))
        }
        coEvery { repository.deleteUser(TestConstants.USER_ID) } returns mockResponse
        val result = deleteUserUseCase.deleteUser(TestConstants.USER_ID)
        result.collect {
            assert(it is Resource.Success)
        }
    }

    @Test
    fun givenRepositoryReturnsError_whenDeleteUserIsCalled_thenReturnError() = runTest {
        val mockResponse: Flow<Resource<Unit>> = flow {
            emit(Resource.Error(ErrorEntity.Network))
        }
        coEvery { repository.deleteUser(TestConstants.USER_ID) } returns mockResponse
        val result = deleteUserUseCase.deleteUser(TestConstants.USER_ID)
        result.collect {
            assert(it is Resource.Error)
            assert((it as Resource.Error).message == ErrorEntity.Network)
        }
    }


}