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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    // State to hold the entered email address. remember saves the state across recompositions.
    var email by remember {
        mutableStateOf("")
    }

    // State to hold the entered password. remember saves the state across recompositions.
    var password by remember {
        mutableStateOf("")
    }

    // State to track if a login process is currently in progress.
    var isLoading by remember {
        mutableStateOf(false)
    }

    // Get the current context for displaying Toast messages or other context-dependent operations.
    val context = LocalContext.current
    // Box composable to stack elements on top of each other, allowing for a background image.
    Box(modifier = Modifier.fillMaxSize()) {
        // Display the background image, filling the entire screen and scaling to fit.
        Image(
            painter = painterResource(id = R.drawable.bg4),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Column to arrange elements vertically in the center of the screen.
        Column(
            modifier = Modifier
                .fillMaxSize() // Make the column take the full screen.
                .padding(14.dp), // Add padding around the column.
            horizontalAlignment = Alignment.CenterHorizontally, // Center the content horizontally.
            verticalArrangement = Arrangement.Top // Arrange items from the top.
        ) {
            // Display the application logo.
            Image(
                painter = painterResource(id = R.drawable.logonhu),
                contentDescription = "Login",
                modifier = Modifier
                    .padding(top = 20.dp) // Add top padding to the logo.
            )

            // Add vertical space below the logo.
            Spacer(modifier = Modifier.height(20.dp))

            // Display the "Welcome" text with specific styling.
            Text(
                text = "Welcome",
                modifier = Modifier
                    .fillMaxWidth() // Make the text take the full width.
                    .padding(2.dp), // Add some padding around the text.
                style = TextStyle(
                    textAlign = TextAlign.Center, // Center the text horizontally.
                    fontSize = 40.sp, // Set the font size.
                    fontFamily = FontFamily.Serif, // Use a serif font.
                    fontWeight = FontWeight.Bold // Make the text bold.
                )
            )

            // Add vertical space below the "Welcome" text.
            Spacer(modifier = Modifier.height(15.dp))

            // Column to group the "Sign in" text, input fields, and buttons.
            Column(
                verticalArrangement = Arrangement.Center, // Arrange items vertically in the center.
                horizontalAlignment = Alignment.CenterHorizontally, // Center the content horizontally.
                modifier = Modifier.fillMaxWidth() // Make the column take the full width.
            ) {

                // Display the "Sign in to your Account" text.
                Text(
                    text = "Sign in to your Account",
                    modifier = Modifier.fillMaxWidth(), // Make the text take the full width.
                    style = TextStyle(
                        textAlign = TextAlign.Center, // Center the text horizontally.
                        fontFamily = FontFamily.Serif, // Use a serif font.
                        fontSize = 22.sp // Set the font size.
                    )
                )

                // Add vertical space below the "Sign in" text.
                Spacer(modifier = Modifier.height(20.dp))

                // OutlinedTextField for the email address input.
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText ->
                        // Update the email state, preventing spaces and newlines.
                        if (!newText.contains(' ') && !newText.contains('\n')) {
                            email = newText
                        }
                    },
                    label = {
                        Text(text = "Email Address") // Label for the email input field.
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Specify the keyboard type.
                    modifier = Modifier.fillMaxWidth() // Make the input field take the full width.
                )

                // Add vertical space below the email input field.
                Spacer(modifier = Modifier.height(10.dp))

                // OutlinedTextField for the password input.
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        // Update the password state, preventing spaces and newlines.
                        if (!newText.contains(' ') && !newText.contains('\n')) {
                            password = newText
                        }
                    },
                    label = {
                        Text(text = "Password") // Label for the password input field.
                    },
                    modifier = Modifier.fillMaxWidth(), // Make the input field take the full width.
                    visualTransformation = PasswordVisualTransformation() // Hide the password characters.
                )

                // Add vertical space below the password input field.
                Spacer(modifier = Modifier.height(20.dp))

                // Box to create a rounded background with a gradient for the Login button.
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
                    // Button for initiating the login process.
                    Button(
                        onClick = {
                            // Check if email or password fields are blank.
                            if (email.isBlank() || password.isBlank()) {
                                // Show a Toast message if either field is empty.
                                AppUtil.showToast(context, "Please enter both email and password")
                            } else {
                                // Set isLoading to true to indicate login in progress.
                                isLoading = true
                                // Call the login function from the AuthViewModel.
                                authViewModel.login(email, password) { success, errorMessage ->
                                    // Callback after the login attempt.
                                    if (success) {
                                        // If login is successful, set isLoading to false.
                                        isLoading = false
                                        // Navigate to the "home" screen, removing the "auth" screen from the back stack.
                                        navController.navigate("home") {
                                            popUpTo("auth") {
                                                inclusive = true
                                            }
                                        }
                                    } else {
                                        // If login fails, set isLoading to false.
                                        isLoading = false
                                        // Show a Toast message with the error message or a generic error.
                                        AppUtil.showToast(context, errorMessage ?: "Something went wrong")
                                    }
                                }
                            }
                        },
                        enabled = !isLoading, // Disable the button while loading.
                        modifier = Modifier
                            .fillMaxWidth() // Make the button take the full width.
                            .height(60.dp), // Set a fixed height for the button.
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Make the button's background transparent to show the box's gradient.
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp), // Add a slight elevation to the button.
                    ) {
                        // Text displayed on the Login button, showing "Logging in..." while loading.
                        Text(
                            text = if (isLoading) "Logging in..." else "Login",
                            fontSize = 22.sp,
                            color = Color.DarkGray
                        )
                    }
                }
                // Add vertical space below the Login button.
                Spacer(modifier = Modifier.height(8.dp))

                // TextButton to navigate to the sign-up screen.
                TextButton(onClick = {
                    navController.navigate("signup") // Navigate to the "signup" route.
                }) {
                    Text(text = "Don't have an account?")
                }

                // TextButton for the "Forgot Password?" option.
                TextButton(onClick = {
                    navController.navigate("signup") // Navigate to the "signup" route (could be a dedicated "forgot password" screen later).
                }) {
                    Text(text = "Forgot Password?")
                }
            }
        }
    }
}
