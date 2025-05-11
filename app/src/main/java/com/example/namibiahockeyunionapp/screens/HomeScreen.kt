package com.example.namibiahockeyunionapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.example.namibiahockeyunionapp.pages.HomePage
import com.example.namibiahockeyunionapp.pages.MatchesPage
import com.example.namibiahockeyunionapp.pages.PlayersPage
import com.example.namibiahockeyunionapp.pages.ProfilePage
import com.example.namibiahockeyunionapp.pages.TeamsPage


@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Players", Icons.Default.Person),
        NavItem("Teams", Icons.Default.AccountBox),
        NavItem("Register", Icons.Default.Add),
        NavItem("Profile", Icons.Default.AccountCircle)
    )

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon , contentDescription = navItem.label)
                        }, label = {
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex)

    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int){
    when(selectedIndex){
        0-> HomePage(modifier)
        1-> PlayersPage(modifier, playersId = "players" )
        2-> TeamsPage(
            modifier, playerId = "players",
            categoryId = "players" ,
            qty = 0
        )
        3-> TeamRegistrationScreen(modifier, navController)
        4-> ProfilePage(modifier)
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,

    )
