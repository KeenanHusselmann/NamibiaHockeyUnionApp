package com.example.namibiahockeyunionapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.viewmodel.AuthViewModel


@Composable
fun TeamRegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    // State to hold the team name entered by the user.
    var teamName by remember {
        mutableStateOf("")
    }

    // State to hold the category of the team entered by the user.
    var category by remember {
        mutableStateOf("")
    }


    // State to track if the team registration process is currently loading.
    var isLoading by remember {
        mutableStateOf(false)
    }


    // Get the current context for displaying Toast messages.
    var context = LocalContext.current

    // Box composable to stack elements on top of each other, allowing for a background image.
    Box(modifier = Modifier.fillMaxSize()) {
        // Display the background image, filling the entire screen and scaling to fit.
        Image(
            painter = painterResource(id = R.drawable.bg4), contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Column to arrange elements vertically in the center of the screen with padding.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Top, // Arrange items from the top.
            horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally.
        ) {
            // Display the application logo.
            Image(
                painter = painterResource(id = R.drawable.logonhu),
                contentDescription = "Login",
                modifier = Modifier
                    .padding(top = 20.dp)


            )

            // Add vertical space below the logo.
            Spacer(modifier = Modifier.height(60.dp))

            // Display the "Register New Team" text with specific styling.
            Text(
                text = "Register New Team",
                modifier = Modifier.fillMaxWidth(), // Make the text take the full width.
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif, // Use a serif font.
                    fontWeight = FontWeight.SemiBold, // Make the text semi-bold.
                    textAlign = TextAlign.Center // Center the text.
                )
            )

            // Add vertical space below the title.
            Spacer(modifier = Modifier.height(20.dp))

            // OutlinedTextField for the user to enter the team name.
            OutlinedTextField(
                value = teamName,
                onValueChange = {
                    teamName = it // Update the teamName state when the text changes.
                },
                label = {
                    Text(text = "Team Name") // Label for the text field.
                },
                modifier = Modifier.fillMaxWidth() // Make the text field take the full width.
            )

            // Add vertical space below the team name text field.
            Spacer(modifier = Modifier.height(10.dp))

            // OutlinedTextField for the user to enter the team category.
            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it // Update the category state when the text changes.
                },
                label = {
                    Text(text = "Category") // Label for the text field.
                },
                modifier = Modifier.fillMaxWidth() // Make the text field take the full width.
            )

            // Add vertical space below the category text field.
            Spacer(modifier = Modifier.height(10.dp))

            // Add vertical space before the register button.
            Spacer(modifier = Modifier.height(20.dp))

            // Box to create a rounded background with a horizontal gradient for the register button.
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Make the box take the full width.
                    .height(50.dp) // Set a fixed height for the box.
                    .background(
                        brush = Brush.horizontalGradient( // Create a horizontal gradient brush.

                            colors = listOf(Color.Green, Color.Cyan) // Define the colors for the gradient.
                        ),
                        shape = RoundedCornerShape(30.dp) // Apply rounded corners to the background.
                    )
            ) {

                // Button to trigger the team registration process.
                Button(
                    onClick = {
                        // Check if either team name or category is blank.
                        when {
                            teamName.isBlank() || category.isBlank() -> AppUtil.showToast(
                                context,
                                "Please enter Team Name and Category" // Show a Toast message if fields are empty.
                            )
                            else -> {
                                isLoading = true // Set loading state to true when registration starts.
                                // Call the teamSignup function from the AuthViewModel.
                                authViewModel.teamSignup(teamName, category) { success, errorMessage ->
                                    isLoading = false // Reset loading state after the registration attempt.
                                    if (success) {
                                        AppUtil.showToast(context, "Team Successfully Registered") // Show success message.
                                        navController.navigate("home") { // Navigate to the "home" screen.
                                            popUpTo("auth") {
                                                inclusive = true // Remove the "auth" screen from the back stack.
                                            }
                                        }
                                    } else {
                                        AppUtil.showToast(
                                            context,
                                            errorMessage ?: "Something went wrong" // Show error message or a generic message.
                                        )
                                    }
                                }
                            }
                        }
                    },
                    enabled = !isLoading, // Disable the button while loading.
                    modifier = Modifier
                        .fillMaxSize() // Make the button fill the entire box.
                        .height(60.dp),
                    colors = ButtonDefaults
                        .buttonColors(containerColor = Color.Transparent), // Make the button's background transparent to show the box's gradient.
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp), // Add a slight elevation to the button.
                ) {
                    // Text displayed on the button, changes based on the loading state.
                    Text(
                        text = if (isLoading) "Registering Team..." else "Register Team",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
