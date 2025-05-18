package com.example.namibiahockeyunionapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namibiahockeyunionapp.pages.NewsDetailsPage
import com.example.namibiahockeyunionapp.pages.NewsHeadlinesPage
import com.example.namibiahockeyunionapp.pages.PlayerDetailsPage
import com.example.namibiahockeyunionapp.pages.PlayersCategoryPage
import com.example.namibiahockeyunionapp.pages.PlayersPage
import com.example.namibiahockeyunionapp.pages.TeamsPage
import com.example.namibiahockeyunionapp.screens.AuthScreen
import com.example.namibiahockeyunionapp.screens.HomeScreen
import com.example.namibiahockeyunionapp.screens.LoginScreen
import com.example.namibiahockeyunionapp.screens.SignUpScreen
import com.example.namibiahockeyunionapp.screens.TeamRegistrationScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

//    This composable function defines the main navigation structure of the Namibia Hockey Union App.
//    It uses Jetpack Compose Navigation to handle transitions between different screens.
//    Modifier to apply styling or layout behavior to this composable.

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    // Creates and remembers a NavHostController. This controller is responsible for managing
    // the state of the navigation within the NavHost.
    val navController = rememberNavController()

    // Assigns the created NavHostController to a global object for potential access from
    // other parts of the application where direct access might not be available.
    GlobalNavigation.navController = navController

    // Checks if there is a currently logged-in user using Firebase Authentication.
    val isLoggedIn = Firebase.auth.currentUser != null
    // Determines the starting destination of the navigation graph based on the user's login status.
    // If the user is logged in, the "home" screen is the starting point; otherwise, it's the "auth" screen.
    val firstPage = if (isLoggedIn) "home" else "auth"

    // Defines the navigation host. This composable sets up the different routes (destinations)
    // that can be navigated to within the application.
    NavHost(navController = navController, startDestination = firstPage) {

        // Defines a composable destination for the "auth" route.
        // When the user navigates to this route, the AuthScreen composable will be displayed.
        composable("auth") {
            AuthScreen(modifier, navController)
        }
        // Defines a composable destination for the "login" route.
        // When the user navigates to this route, the LoginScreen composable will be displayed.
        composable("login") {
            LoginScreen(modifier, navController)
        }
        // Defines a composable destination for the "signup" route.
        // When the user navigates to this route, the SignUpScreen composable will be displayed.
        composable("signup") {
            SignUpScreen(modifier, navController)
        }
        // Defines a composable destination for the "teamSignup" route.
        // When the user navigates to this route, the TeamRegistrationScreen composable will be displayed.
        composable("teamSignup") {
            TeamRegistrationScreen(modifier, navController)
        }

        // Defines a composable destination for the "home" route.
        // When the user navigates to this route, the HomeScreen composable will be displayed.
        composable("home") {
            HomeScreen(modifier, navController)
        }

        // Defines a composable destination for the "teams" route.
        // When the user navigates to this route, the TeamsPage composable will be displayed.
        // Currently, it passes default or empty values for categoryId, playerId, and qty.
        composable("teams") {
            TeamsPage(modifier, navController = navController, categoryId = "", playerId = "", qty = 0)
        }

        // Defines a composable destination for the "news-headlines" route with a dynamic parameter "categoryId".
        // The value of categoryId will be passed in the navigation URL.
        composable("news-headlines/{categoryId}") { backStackEntry ->
            // Retrieves the "categoryId" argument from the previous navigation back stack entry.
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            // Displays the NewsHeadlinesPage, passing the retrieved categoryId (or an empty string if it's null).
            NewsHeadlinesPage(modifier, categoryId ?: "")
        }
        // Defines a composable destination for the "news-details" route with a dynamic parameter "headlineId".
        // The value of headlineId will be passed in the navigation URL.
        composable("news-details/{headlineId}") { backStackEntry ->
            // Retrieves the "headlineId" argument from the previous navigation back stack entry.
            val headlineId = backStackEntry.arguments?.getString("headlineId")
            // Displays the NewsDetailsPage, passing the retrieved headlineId (or an empty string if it's null).
            NewsDetailsPage(modifier, headlineId ?: "")
        }
        // Defines a composable destination for the "players-category" route with a dynamic parameter "playerCategoryId".
        // The value of playerCategoryId will be passed in the navigation URL.
        composable("players-category/{playerCategoryId}") { backStackEntry ->
            // Retrieves the "playerCategoryId" argument from the previous navigation back stack entry.
            val playerCategoryId = backStackEntry.arguments?.getString("playerCategoryId")
            // Displays the PlayersCategoryPage, passing the retrieved playerCategoryId (or an empty string if it's null).
            PlayersCategoryPage(modifier, playerCategoryId ?: "")
        }
        // Defines a composable destination for the "player-details" route with a dynamic parameter "playerId".
        // The value of playerId will be passed in the navigation URL.
        composable("player-details/{playerId}") { backStackEntry ->
            // Retrieves the "playerId" argument from the previous navigation back stack entry.
            val playerId = backStackEntry.arguments?.getString("playerId")
            // Displays the PlayerDetailsPage, passing the retrieved playerId (or an empty string if it's null).
            PlayerDetailsPage(modifier, playerId ?: "")
        }
    }
}

//    An object that holds a static reference to the NavHostController.
//    This allows navigation to be triggered from anywhere in the application that has access to this object.
object GlobalNavigation {
    lateinit var navController: NavHostController
}
