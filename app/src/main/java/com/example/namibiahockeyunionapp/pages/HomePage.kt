package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.Column // Used for arranging UI elements vertically.
import androidx.compose.foundation.layout.Spacer // Used to add empty space between UI elements.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.height // Modifier to set the height of a composable.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.material3.Button // A Material Design button composable.
import androidx.compose.material3.Text // A Material Design text composable for displaying text.
import androidx.compose.runtime.Composable // Marks a function as a Composable function, the building block of Compose UI.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.layout.ContentScale // Defines how an image should be scaled within its bounds.
import androidx.compose.ui.text.TextStyle // Defines styling for text, like font, size, weight, etc.
import androidx.compose.ui.text.font.FontWeight // Defines the thickness of the font glyphs.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.
import androidx.compose.ui.unit.sp // Dimension unit representing scaled pixels for text size.
import com.example.namibiahockeyunionapp.GlobalNavigation.navController // Imports the globally defined NavController for navigation.
import com.example.namibiahockeyunionapp.components.Banner // Imports a custom Composable function for a banner.
import com.example.namibiahockeyunionapp.components.Header // Imports a custom Composable function for a header.
import com.example.namibiahockeyunionapp.components.News // Imports a custom Composable function for displaying news.
import androidx.compose.foundation.layout.Box // Used for layering UI elements on top of each other.
import com.google.firebase.Firebase // Imports the main Firebase object.
import com.google.firebase.auth.auth // Imports the Firebase Authentication instance.
import androidx.compose.ui.res.painterResource // Helper function to load drawable resources as a Painter.
import androidx.compose.foundation.Image // Composable for displaying an image.
import androidx.compose.ui.text.font.FontFamily // Defines the font family for text.
import com.example.namibiahockeyunionapp.R // Imports the generated resource file, containing references to drawables, strings, etc.

/**
 * Composable function representing the Home Page of the application.
 * This screen displays a background image, header, banner, news section, and a logout button.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 */
@Composable
fun HomePage(modifier: Modifier = Modifier) {

    // Box layout allows stacking elements on top of each other.
    // Here, it's used to place the background image underneath the main content column.
    Box(modifier = Modifier.fillMaxSize()) {
        // Displays a background image.
        Image(
            // Loads the image from the drawable resources.
            painter = painterResource(id = R.drawable.bg4),
            // Provides a description for accessibility.
            contentDescription = "Background Image",
            // Modifiers applied to the image.
            modifier = Modifier.fillMaxSize(), // Make the image fill the entire size of the Box.
            contentScale = ContentScale.Crop // Scale the image to fill the bounds, cropping if necessary.
        )

        // Column layout arranges its children vertically.
        // This column holds the main content of the page, placed on top of the background image.
        Column(
            modifier = Modifier
                .fillMaxSize() // Make the column fill the entire size of the Box.
                .padding(10.dp) // Add padding around the content inside the column.
        ) {

            // Displays the custom Header composable.
            Header()

            // Displays the custom Banner composable with a fixed height.
            Banner(modifier = Modifier.height(150.dp))

            // Adds vertical space between the banner and the news section title.
            Spacer(modifier = Modifier.height(40.dp))

            // Displays the "Sports News" title text.
            Text(
                "Sports News",
                // Defines the text style for the title.
                style = TextStyle(
                    fontFamily = FontFamily.Serif, // Set the font family to Serif.
                    fontWeight = FontWeight.Bold, // Make the text bold.
                    fontSize = 28.sp // Set the font size.
                )
            )
            // Adds vertical space between the title and the news list.
            Spacer(modifier = Modifier.height(20.dp))

            // Displays the custom News composable, presumably showing a list of news items.
            News(modifier) // Passes the modifier down to the News composable.

            // Displays a Button.
            Button(
                // Defines the action to perform when the button is clicked.
                onClick = {
                    // Sign out the current user from Firebase Authentication.
                    Firebase.auth.signOut()
                    // Navigate to the "auth" destination (presumably the authentication screen).
                    navController.navigate("auth") {
                        // Configure navigation: pop up to the "home" destination (remove it from the back stack)
                        // and include the "home" destination itself in the pop operation.
                        // This clears the back stack up to and including the home screen,
                        // preventing the user from navigating back to the home screen after logging out.
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
            {
                // Text displayed inside the button.
                Text(text = "Logout")
            }
        }
    }
}
