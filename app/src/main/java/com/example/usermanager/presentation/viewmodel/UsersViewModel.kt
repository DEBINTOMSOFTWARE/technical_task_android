package com.example.usermanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usermanager.domain.usecase.GetUsers
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
    private val getUsersUseCase: GetUsers
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    private val _intent = MutableSharedFlow<UserIntent>()
    private val intent: SharedFlow<UserIntent> = _intent

    init {
        processIntents(UserIntent.LoadUsers)
        viewModelScope.launch {
            onIntent(UserIntent.LoadUsers)
        }
    }

    fun onIntent(userIntent: UserIntent) {
        viewModelScope.launch {
            _intent.emit(userIntent)
        }
    }

    fun processIntents(intent: UserIntent) {
        viewModelScope.launch {
                when (intent) {
                    UserIntent.LoadUsers -> loadUsers()
                    UserIntent.exitUser -> onExit()
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
}