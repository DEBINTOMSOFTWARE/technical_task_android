package com.example.usermanager.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.usermanager.TestConstants
import com.example.usermanager.addRequestData
import com.example.usermanager.domain.usecase.GetUsers
import com.example.usermanager.domain.usecase.adduser.AddUser
import com.example.usermanager.presentation.UsersUIState
import com.example.usermanager.presentation.intent.UserIntent
import com.example.usermanager.user
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class UsersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getUsersUseCase: GetUsers
    private lateinit var addUserUseCase: AddUser
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mockk()
        addUserUseCase = mockk()
        viewModel = UsersViewModel(getUsersUseCase, addUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun givenViewModel_whenLoadUsers_thenUpdatesUIStateWithUsers() = testScope.runTest {
        val users = listOf(user)

        coEvery { getUsersUseCase.getUsers(TestConstants.PAGE) } returns flow {
            emit(Resource.Success(users))
        }

        val states = mutableListOf<UsersUIState>()
        val job = launch {
            viewModel.uiState.collect { state -> states.add(state) }
        }

        assertEquals(UsersUIState(), viewModel.uiState.value)
        viewModel.processIntents(UserIntent.LoadUsers)
        viewModel.loadUsers()

        advanceUntilIdle()

        assertEquals(
            UsersUIState(
               users = users,
                isLoading = false,
                error = null
            ),
            viewModel.uiState.value
        )

        coVerify { getUsersUseCase.getUsers(TestConstants.PAGE) }
        job.cancel()
    }

    @Test
    fun givenViewModel_whenLoadUsers_thenUpdatesUIStateWithError() = testScope.runTest {

        coEvery { getUsersUseCase.getUsers(TestConstants.PAGE) } returns flow {
            emit(Resource.Error(ErrorEntity.Network))
        }

        val states = mutableListOf<UsersUIState>()
        val job = launch {
            viewModel.uiState.collect { state -> states.add(state) }
        }

        assertEquals(UsersUIState(), viewModel.uiState.value)
        viewModel.processIntents(UserIntent.LoadUsers)
        viewModel.loadUsers()

        advanceUntilIdle()

        assertEquals(
            UsersUIState(
                users = emptyList(),
                isLoading = false,
                error = ErrorEntity.Network
            ),
            viewModel.uiState.value
        )

        coVerify { getUsersUseCase.getUsers(TestConstants.PAGE) }
        job.cancel()
    }

    @Test
    fun givenViewModel_whenAddUser_thenUpdatesUIStateWithUser() = testScope.runTest {
        val user = user
        coEvery { addUserUseCase.addUser(userData = addRequestData) } returns flow {
            emit(Resource.Success(user))
        }

        val states = mutableListOf<UsersUIState>()
        val job = launch {
            viewModel.uiState.collect { state -> states.add(state) }
        }

        assertEquals(UsersUIState(), viewModel.uiState.value)

        viewModel.processIntents(UserIntent.AddUser(
            name = TestConstants.NAME,
            email = TestConstants.EMAIL,
            gender = TestConstants.GENDER,
            status = TestConstants.STATUS
        ))
        viewModel.addUser(
            name = TestConstants.NAME,
            email = TestConstants.EMAIL,
            gender = TestConstants.GENDER,
            status = TestConstants.STATUS
        )

        advanceUntilIdle()

        assertEquals(
            UsersUIState(
                user = user,
                isLoading = false,
                error = null
            ),
            viewModel.uiState.value
        )

        coVerify { addUserUseCase.addUser(addRequestData) }
        job.cancel()
    }

    @Test
    fun givenViewModel_whenAddUser_thenUpdatesUIStateWithError() = testScope.runTest {
        coEvery { addUserUseCase.addUser(addRequestData) } returns flow {
            emit(Resource.Error(ErrorEntity.Network))
        }

        val states = mutableListOf<UsersUIState>()
        val job = launch {
            viewModel.uiState.collect { state -> states.add(state) }
        }

        assertEquals(UsersUIState(), viewModel.uiState.value)
        viewModel.onIntent(UserIntent.AddUser(
            name = TestConstants.NAME,
            email = TestConstants.EMAIL,
            gender = TestConstants.GENDER,
            status = TestConstants.STATUS
        ))
        viewModel.addUser(
            name = TestConstants.NAME,
            email = TestConstants.EMAIL,
            gender = TestConstants.GENDER,
            status = TestConstants.STATUS
        )

        advanceUntilIdle()

        assertEquals(
            UsersUIState(
                user = null,
                isLoading = false,
                error = ErrorEntity.Network
            ),
            viewModel.uiState.value
        )

        coVerify { addUserUseCase.addUser(addRequestData) }
        job.cancel()
    }
}