package com.example.usermanager.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.usermanager.domain.model.UserListItemEntity
import com.example.usermanager.presentation.components.BodyText
import com.example.usermanager.presentation.components.UserItemView
import com.example.usermanager.presentation.components.dialogs.ErrorDialog
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

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.app_bar_height))
                    .semantics { contentDescription = appBarTitle },
                title = { BodyText(text = appBarTitle) },
                backgroundColor = MaterialTheme.colorScheme.primary
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
                uiState.users.isNotEmpty() -> {
                    showUsersList(users = uiState.users)
                }
                uiState.error != null -> {
                    when(uiState.error) {
                        ErrorEntity.Network -> {
                           ErrorDialog(
                               title = "Error !",
                               headerText = stringResource(id = R.string.network_unavailable_header_label),
                               bodyText = stringResource(id = R.string.network_unavailable_body_label),
                               onDismiss = {  },
                               onTryAgain = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                               onExit = { usersViewModel.processIntents(UserIntent.exitUser) }
                           )
                        }
                        ErrorEntity.NotFound -> {
                            ErrorDialog(
                                title = "Error !",
                                headerText = stringResource(id = R.string.user_not_found_header_label),
                                bodyText = stringResource(id = R.string.user_not_found_body_label),
                                onDismiss = {  },
                                onTryAgain = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onExit = {  usersViewModel.processIntents(UserIntent.exitUser)}
                            )
                        }
                        ErrorEntity.AccessDenied -> {
                            ErrorDialog(
                                title = "Error !",
                                headerText = stringResource(id = R.string.access_denied_header_label),
                                bodyText = stringResource(id = R.string.access_denied_body_label),
                                onDismiss = {  },
                                onTryAgain = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onExit = { usersViewModel.processIntents(UserIntent.exitUser) }
                            )
                        }
                        ErrorEntity.ServiceUnavailable -> {
                            ErrorDialog(
                                title = "Error !",
                                headerText = stringResource(id = R.string.service_unavailable_header_label),
                                bodyText = stringResource(id = R.string.service_unavailable_body_label),
                                onDismiss = {  },
                                onTryAgain = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onExit = { usersViewModel.processIntents(UserIntent.exitUser) }
                            )
                        }
                        else -> {
                            ErrorDialog(
                                title = "Error !",
                                headerText = stringResource(id = R.string.unknown_error_header_label),
                                bodyText = stringResource(id = R.string.unknown_error_body_label),
                                onDismiss = {  },
                                onTryAgain = { usersViewModel.processIntents(UserIntent.LoadUsers) },
                                onExit = { usersViewModel.processIntents(UserIntent.exitUser) }
                            )
                        }
                    }
                }
                uiState.exit -> {
                    val context = LocalContext.current
                    (context as? MainActivity)?.finish()
                }
            }
        }
    }


}

@Composable
fun showUsersList(
    users: List<UserListItemEntity>,
) {
    val usesListLabel = "Users List"
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = Modifier.semantics { contentDescription =  usesListLabel},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(users) { user ->
            UserItemView(user)
        }
    }
}

