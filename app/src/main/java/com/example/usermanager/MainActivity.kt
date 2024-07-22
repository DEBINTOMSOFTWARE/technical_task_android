package com.example.usermanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.usermanager.presentation.ui.UserListScreen
import com.example.usermanager.ui.theme.UserManagerTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    data object UsersListScreen : Destination("users_list_screen")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserManagerTheme {
                AppNavigation(
                    navController = rememberNavController()
                )
            } 
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Destination.UsersListScreen.route) {
        composable(Destination.UsersListScreen.route) {
            UserListScreen(navController = navController)
        }
    }
}

