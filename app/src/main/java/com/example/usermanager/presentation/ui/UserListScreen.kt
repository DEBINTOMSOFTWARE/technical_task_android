package com.example.usermanager.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import com.example.usermanager.MainActivity
import com.example.usermanager.R
import com.example.usermanager.domain.model.UserItemEntity
import com.example.usermanager.presentation.components.BodyText
import com.example.usermanager.presentation.components.UserItemView
import com.example.usermanager.presentation.components.dialogs.AddUserDialog
import com.example.usermanager.presentation.components.dialogs.UMAlertDialog
import com.example.usermanager.presentation.intent.UserIntent
import com.example.usermanager.presentation.viewmodel.UsersViewModel
import com.example.usermanager.utils.ErrorEntity


@Composable
fun UserListScreen(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {
    val appBarTitle = stringResource(id = R.string.app_name)
    val uiState by usersViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        usersViewModel.onIntent(UserIntent.LoadUsers)
    }
    var showAddUserDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<UserItemEntity?>(null) }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.app_bar_height))
                    .semantics { contentDescription = appBarTitle },
                title = { BodyText(text = appBarTitle) },
                backgroundColor = MaterialTheme.colorScheme.primary,
                actions = {
                    IconButton(onClick = {
                        showAddUserDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    when (uiState.error) {
                        ErrorEntity.Network -> {
                            UMAlertDialog(
                                title = stringResource(R.string.error_title_label),
                                headerText = stringResource(id = R.string.network_unavailable_header_label),
                                bodyText = stringResource(id = R.string.network_unavailable_body_label),
                                onDismiss = { },
                                onPositiveButtonClick = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onNegativeButtonClick = { usersViewModel.processIntents(UserIntent.ExitUser) }
                            )
                        }

                        ErrorEntity.NotFound -> {
                            UMAlertDialog(
                                title = stringResource(R.string.error_title_label),
                                headerText = stringResource(id = R.string.user_not_found_header_label),
                                bodyText = stringResource(id = R.string.user_not_found_body_label),
                                onDismiss = { },
                                onPositiveButtonClick = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onNegativeButtonClick = { usersViewModel.processIntents(UserIntent.ExitUser) }
                            )
                        }

                        ErrorEntity.AccessDenied -> {
                            UMAlertDialog(
                                title = stringResource(R.string.error_title_label),
                                headerText = stringResource(id = R.string.access_denied_header_label),
                                bodyText = stringResource(id = R.string.access_denied_body_label),
                                onDismiss = { },
                                onPositiveButtonClick = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onNegativeButtonClick = { usersViewModel.processIntents(UserIntent.ExitUser) }
                            )
                        }

                        ErrorEntity.ServiceUnavailable -> {
                            UMAlertDialog(
                                title = stringResource(R.string.error_title_label),
                                headerText = stringResource(id = R.string.service_unavailable_header_label),
                                bodyText = stringResource(id = R.string.service_unavailable_body_label),
                                onDismiss = { },
                                onPositiveButtonClick = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onNegativeButtonClick = { usersViewModel.processIntents(UserIntent.ExitUser) }
                            )
                        }

                        else -> {
                            UMAlertDialog(
                                title = stringResource(R.string.error_title_label),
                                headerText = stringResource(id = R.string.unknown_error_header_label),
                                bodyText = stringResource(id = R.string.unknown_error_body_label),
                                onDismiss = { },
                                onPositiveButtonClick = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onNegativeButtonClick = { usersViewModel.processIntents(UserIntent.ExitUser) }
                            )
                        }
                    }
                }

                uiState.users.isNotEmpty() -> {
                    showUsersList(users = uiState.users, onLongPressUser = { user ->
                        selectedUser = user
                    })
                }

                uiState.exit -> {
                    val context = LocalContext.current
                    (context as? MainActivity)?.finish()
                }
            }

            selectedUser?.let { user ->
                UMAlertDialog(
                    title = stringResource(R.string.delete_user_title_label),
                    headerText = "",
                    bodyText = stringResource(R.string.delete_user_body_label),
                    onDismiss = { },
                    onPositiveButtonClick = {
                        usersViewModel.processIntents(UserIntent.DeleteUser(user.id))
                        selectedUser = null
                    },
                    onNegativeButtonClick = { selectedUser = null },
                    positiveButtonText = stringResource(R.string.delete_button_label),
                    negativeButtonText = stringResource(id = R.string.cancel_label)
                )
            }
        }
    }

    if (showAddUserDialog) {
        AddUserDialog(
            title = stringResource(id = R.string.add_new_user_label),
            onDismiss = { showAddUserDialog = false },
            onAddUser = { name, email, gender, isActive ->
                println("Name: $name, Email: $email")
                println("Gender: $gender, Status: $isActive")
                usersViewModel.processIntents(
                    UserIntent.AddUser(
                        name,
                        email,
                        gender,
                        if (isActive) "Active" else "Inactive"
                    )
                )
                showAddUserDialog = false
            }
        )
    }
}

@Composable
fun showUsersList(
    users: List<UserItemEntity>,
    onLongPressUser: (UserItemEntity) -> Unit
) {
    val usesListLabel = "Users List"
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier.semantics { contentDescription = usesListLabel },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(users) { user ->
            UserItemView(user, onLongPress = {
                onLongPressUser(user)
            })
        }
    }
}

