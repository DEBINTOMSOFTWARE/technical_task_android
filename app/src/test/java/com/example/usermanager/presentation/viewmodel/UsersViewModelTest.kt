package com.example.usermanager.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.usermanager.TestConstants
import com.example.usermanager.addRequestData
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.domain.usecase.adduser.AddUser
import com.example.usermanager.domain.usecase.deleteUser.DeleteUser
import com.example.usermanager.domain.usecase.getusers.GetUsers
import com.example.usermanager.presentation.UsersUIState
import com.example.usermanager.presentation.intent.UserIntent
import com.example.usermanager.user
import com.example.usermanager.utils.ErrorEntity
import com.example.usermanager.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import kotlin.test.assertEquals

class UsersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getUsersUseCase: GetUsers
    private lateinit var addUserUseCase: AddUser
    private lateinit var deleteUserUseCase: DeleteUser
    private lateinit var mockSavedStateHandle: SavedStateHandle
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mockk()
        addUserUseCase = mockk()
        deleteUserUseCase = mockk()
        mockSavedStateHandle = mockk(relaxed = true)

        every { mockSavedStateHandle.get<List<UserItemEntity>>("users") } returns null
        every { mockSavedStateHandle.set("users", any<List<UserItemEntity>>()) } answers { Unit }

        viewModel = UsersViewModel(getUsersUseCase, addUserUseCase, deleteUserUseCase, mockSavedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}