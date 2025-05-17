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

@Composable
fun AppNavigation(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    GlobalNavigation.navController = navController

   val isLoggedIn = Firebase.auth.currentUser != null
   val firstPage = if (isLoggedIn) "home" else "auth"

    NavHost(navController = navController, startDestination = firstPage){

        composable("auth"){
            AuthScreen(modifier, navController)
        }
        composable("login"){
            LoginScreen(modifier, navController)
        }
        composable("signup"){
            SignUpScreen(modifier,navController)
        }
        composable("teamSignup"){
            TeamRegistrationScreen(modifier,navController)
        }

        composable("home"){
            HomeScreen(modifier, navController)
        }

        composable("teams"){
            TeamsPage(modifier, navController = navController, categoryId = "", playerId = "", qty = 0)
        }

        composable("news-headlines/{categoryId}"){
           val categoryId = it.arguments?.getString("categoryId")
            NewsHeadlinesPage(modifier, categoryId?: "")
        }
        composable("news-details/{headlineId}"){
            val headlineId = it.arguments?.getString("headlineId")
            NewsDetailsPage(modifier, headlineId?: "")
        }
        composable(" /{playerCategoryId}"){
            val playerCategoryId = it.arguments?.getString("playerCategoryId")
            PlayersCategoryPage(modifier, playerCategoryId?: "")
        }
        composable("player-details/{playerId}"){
            val playerId = it.arguments?.getString("playerId")
            PlayerDetailsPage(modifier, playerId?: "")
        }
    }
}

object GlobalNavigation{
   lateinit var navController: NavHostController
}