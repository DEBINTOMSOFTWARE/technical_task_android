package com.example.usermanager.domain.usecase

import com.example.usermanager.TestConstants
import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.domain.repository.UsersRepository
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserImplTest {

    private lateinit var repository: UsersRepository
    private lateinit var getUsersUseCase: GetUsers

    @Before
    fun setUp() {
        repository = mockk()
        getUsersUseCase = GetUsersImpl(repository)
    }

    @Test
    fun givenRepositoryReturnsUsers_whenGetUsersIsCalled_thenReturnUsers() = runTest {
        val mockResponse : Flow<Resource<List<UserListItemEntity>>> = flow {
            val users = listOf(
                UserListItemEntity(
                    email = TestConstants.EMAIL,
                    gender = TestConstants.GENDER,
                    id = TestConstants.ID,
                    name = TestConstants.NAME,
                    status = TestConstants.STATUS
                )
            )
            emit(Resource.Success(users))
        }
        coEvery { repository.getUsers(TestConstants.PAGE) } returns mockResponse
        val result = getUsersUseCase.getUsers(TestConstants.PAGE)
        result.collect {
            assert(it is Resource.Success)
            val users = (it as Resource.Success).data
            assert(users?.size == 1)
            assert(users?.get(0)?.email == TestConstants.EMAIL)
        }
    }

    @Test
    fun givenRepositoryReturnsError_whenGetUsersIsCalled_thenReturnError() = runTest {
        val mockResponse : Flow<Resource<List<UserListItemEntity>>> = flow {
            emit(Resource.Error(ErrorEntity.Network))
        }
        coEvery { repository.getUsers(TestConstants.PAGE) } returns mockResponse
        val result = getUsersUseCase.getUsers(TestConstants.PAGE)
        result.collect {
            assert(it is Resource.Error)
            assert((it as Resource.Error).message == ErrorEntity.Network)
        }
    }
}