package com.example.namibiahockeyunionapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
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
import com.example.namibiahockeyunionapp.pages.PlayersPage
import com.example.namibiahockeyunionapp.pages.ProfilePage
import com.example.namibiahockeyunionapp.pages.TeamsPage


@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    // HomeScreen is a composable function that serves as the main screen with a bottom navigation bar.
    // It takes an optional Modifier for styling and a NavController for handling navigation.

    // navItemList is a list of NavItem data classes, each representing an item in the bottom navigation bar.
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home), // Represents the "Home" navigation item with the Home icon.
        NavItem("Players", Icons.Default.Person), // Represents the "Players" navigation item with the Person icon.
        NavItem("Teams", Icons.Default.People), // Represents the "Teams" navigation item with the People icon.
        NavItem("Register", Icons.Default.Add), // Represents the "Register" navigation item with the Add icon.
        NavItem("Profile", Icons.Default.AccountCircle) // Represents the "Profile" navigation item with the AccountCircle icon.
    )

    // selectedIndex is a state variable that keeps track of the currently selected item in the bottom navigation bar.
    // remember { mutableStateOf(0) } creates a mutable state that is remembered across recompositions, initialized to 0 (the first item).
    var selectedIndex by remember { mutableStateOf(0) }

    // Scaffold provides the basic visual structure for a screen, handling elements like top bar, bottom bar, and body content.
    Scaffold(
        bottomBar = {
            // NavigationBar is a composable that displays a row of navigation items, typically at the bottom of the screen.
            NavigationBar {
                // Iterate through the navItemList to create each navigation item.
                navItemList.forEachIndexed { index, navItem ->
                    // NavigationBarItem represents a single item within the NavigationBar.
                    NavigationBarItem(
                        selected = index == selectedIndex, // Determines if the current item is selected based on the selectedIndex.
                        onClick = {
                            // When a navigation item is clicked, update the selectedIndex to the index of the clicked item.
                            selectedIndex = index
                        },
                        icon = {
                            // Icon composable displays the icon associated with the current navigation item.
                            Icon(imageVector = navItem.icon , contentDescription = navItem.label) // navItem.icon is the ImageVector, and navItem.label provides a content description for accessibility.
                        }, label = {
                            // Text composable displays the label of the current navigation item.
                            Text(text = navItem.label)
                        }
                    )
                }
            }
        }
    ) {
        // ContentScreen is a composable function that displays different content based on the selectedIndex in the bottom navigation bar.
        // modifier.padding(it) applies the padding provided by the Scaffold to the content area, ensuring the content is not obscured by the bottom bar.
        ContentScreen(modifier = modifier.padding(it), selectedIndex)

    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int){
    // ContentScreen is a composable function that displays different screens based on the selected index.
    // It takes a Modifier for styling and the selectedIndex to determine which content to show.
    when(selectedIndex){
        // When selectedIndex is 0, display the HomePage.
        0-> HomePage(modifier)
        // When selectedIndex is 1, display the PlayersPage, passing a fixed playersId.
        1-> PlayersPage(modifier, playersId = "players" )
        // When selectedIndex is 2, display the TeamsPage, passing fixed categoryId, playerId, qty, and the NavController.
        2-> TeamsPage(modifier, categoryId = "teams", playerId = "players", qty = 1, navController)
        // When selectedIndex is 3, display the TeamRegistrationScreen, passing the Modifier and NavController.
        3-> TeamRegistrationScreen(modifier, navController)
        // When selectedIndex is 4, display the ProfilePage, passing the Modifier, NavController, and an empty lambda for onLogout.
        4-> ProfilePage(modifier, navController, onLogout = {})
    }
}

// NavItem is a data class that represents an item in the bottom navigation bar.
data class NavItem(
    val label: String, // The text label for the navigation item.
    val icon: ImageVector, // The icon to be displayed for the navigation item.
)