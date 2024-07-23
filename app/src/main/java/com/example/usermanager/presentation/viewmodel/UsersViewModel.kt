package com.example.usermanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermanager.domain.model.AddUserRequestDataEntity
import com.example.usermanager.domain.usecase.GetUsers
import com.example.usermanager.domain.usecase.adduser.AddUser
import com.example.usermanager.presentation.UsersUIState
import com.example.usermanager.presentation.intent.UserIntent
import com.example.usermanager.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsers,
    private val addUserUseCase: AddUser

) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    private val _intent = MutableSharedFlow<UserIntent>()
    private val intent: SharedFlow<UserIntent> = _intent


    fun onIntent(userIntent: UserIntent) {
        viewModelScope.launch {
            _intent.emit(userIntent)
            processIntents(userIntent)
        }
    }

    fun processIntents(intent: UserIntent) {
        viewModelScope.launch {
                when (intent) {
                    is UserIntent.LoadUsers -> loadUsers()
                    is UserIntent.AddUser -> addUser(intent.name, intent.email, intent.gender, intent.status)
                    is UserIntent.ExitUser -> onExit()
                }
        }
    }

    private fun onExit() {
        _uiState.value = UsersUIState(exit = true)
    }

    suspend fun loadUsers() {
        withContext(Dispatchers.IO) {
            getUsersUseCase.getUsers(1).collect { resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.value = UsersUIState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _uiState.value = UsersUIState(users = resource.data ?: emptyList())
                        }

                        is Resource.Error -> {
                            _uiState.value = UsersUIState(error = resource.message)
                        }
                    }

                }
            }
        }
    }

    suspend fun addUser(name: String, email: String, gender: String, status: String) {
        withContext(Dispatchers.IO) {
            val addUserRequestData = AddUserRequestDataEntity(
                name = name,
                email = email,
                gender = gender,
                status = status
            )
            addUserUseCase.addUser(addUserRequestData).collect { resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            val newUser = resource.data
                            if(newUser != null) {
                                val updatedUsers =  listOf(newUser) + _uiState.value.users  // Append new user to existing list
                                _uiState.value = _uiState.value.copy(
                                    user = newUser,
                                    users = updatedUsers,
                                    isLoading = false,
                                    error = null
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    user = null,
                                    users = _uiState.value.users,
                                    isLoading = false,
                                    error = null
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = resource.message,
                                users = _uiState.value.users
                            )
                        }
                    }
                }
            }
        }
    }
}