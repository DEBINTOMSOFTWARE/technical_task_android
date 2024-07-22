package com.example.usermanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usermanager.presentation.ui.UserListScreen
import com.example.usermanager.presentation.viewmodel.UsersViewModel
import com.example.usermanager.ui.theme.UserManagerTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    data object UsersListScreen : Destination("users_list_screen")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            UserManagerTheme {
                AppNavigation(
                    navController = rememberNavController(),
                    usersViewModel = viewModel
                )
            } 
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {
    NavHost(navController = navController, startDestination = Destination.UsersListScreen.route) {
        composable(Destination.UsersListScreen.route) {
            UserListScreen(navController = navController, usersViewModel = usersViewModel)
        }
    }
}

