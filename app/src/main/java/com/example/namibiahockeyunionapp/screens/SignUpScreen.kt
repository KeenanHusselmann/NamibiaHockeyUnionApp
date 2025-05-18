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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.viewmodel.AuthViewModel


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    // State to hold the entered email address. remember ensures the value persists across recompositions.
    var email by remember {
        mutableStateOf("")
    }

    // State to hold the entered full name. remember ensures the value persists across recompositions.
    var name by remember {
        mutableStateOf("")
    }

    // State to hold the entered password. remember ensures the value persists across recompositions.
    var password by remember {
        mutableStateOf("")
    }

    // State to track if the sign-up process is currently loading. remember ensures the value persists.
    var isLoading by remember {
        mutableStateOf(false)
    }


    // Get the current context for displaying Toast messages or other context-related operations.
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
                .fillMaxSize() // Make the column take the full available space.
                .padding(14.dp), // Add padding around the column.
            verticalArrangement = Arrangement.Top, // Arrange items from the top.
            horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally.
        ) {
            // Display the application logo.
            Image(
                painter = painterResource(id = R.drawable.logonhu),
                contentDescription = "Login",
                modifier = Modifier
                    .padding(top = 20.dp) // Add top padding to the logo.
            )

            // Add vertical space between the logo and the "Create an Account" text.
            Spacer(modifier = Modifier.height(20.dp))

            // Display the "Create an Account" text with specific styling.
            Text(
                text = "Create an Account",
                modifier = Modifier.fillMaxWidth(), // Make the text take the full width.
                style = TextStyle(
                    textAlign = TextAlign.Center, // Center the text horizontally.
                    fontSize = 30.sp, // Set the font size.
                    fontFamily = FontFamily.Serif, // Use a serif font.
                    fontWeight = FontWeight.Bold // Make the text bold.
                )
            )
            // Add vertical space below the title.
            Spacer(modifier = Modifier.height(20.dp))

            // Outlined text field for entering the email address.
            OutlinedTextField(
                value = email, // Bind the text field's value to the 'email' state.
                onValueChange = { newText ->
                    // Update the 'email' state with the new text, preventing spaces and newlines.
                    if (!newText.contains(' ') && !newText.contains('\n')) {
                        email = newText
                    }
                },
                label = { // Label displayed when the text field is empty.
                    Text(text = "Email Address")
                },
                modifier = Modifier.fillMaxWidth() // Make the text field take the full width.
            )

            // Add vertical space below the email field.
            Spacer(modifier = Modifier.height(10.dp))

            // Outlined text field for entering the full name.
            OutlinedTextField(
                value = name, // Bind the text field's value to the 'name' state.
                onValueChange = { newText ->
                    // Update the 'name' state with the new text, preventing spaces and newlines.
                    if (!newText.contains(' ') && !newText.contains('\n')) {
                        name = newText
                    }
                },
                label = { // Label displayed when the text field is empty.
                    Text(text = "Full Name")
                },
                modifier = Modifier.fillMaxWidth() // Make the text field take the full width.
            )

            // Add vertical space below the name field.
            Spacer(modifier = Modifier.height(10.dp))

            // Outlined text field for entering the password.
            OutlinedTextField(
                value = password, // Bind the text field's value to the 'password' state.
                onValueChange = { newText ->
                    // Update the 'password' state with the new text, preventing spaces and newlines.
                    if (!newText.contains(' ') && !newText.contains('\n')) {
                        password = newText
                    }
                },
                label = { // Label displayed when the text field is empty.
                    Text(text = "Password")
                },
                modifier = Modifier.fillMaxWidth(), // Make the text field take the full width.
                visualTransformation = PasswordVisualTransformation() // Hide the password characters.
            )

            // Add vertical space before the "Create Account" button.
            Spacer(modifier = Modifier.height(40.dp))

            // Box to create a rounded background with a gradient for the "Create Account" button.
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Make the box take the full width.
                    .height(50.dp) // Set a fixed height for the box.
                    .background(
                        brush = Brush.horizontalGradient( // Create a horizontal gradient brush.
                            colors = listOf(Color.Blue, Color.Magenta) // Define the colors for the gradient.
                        ),
                        shape = RoundedCornerShape(30.dp) // Apply rounded corners to the background.
                    )
            ) {


                // Button for creating a new account.
                Button(
                    onClick = {
                        // Check if any of the input fields are blank.
                        if (email.isBlank() || name.isBlank() || password.isBlank()) {
                            // Display a toast message if any field is empty.
                            AppUtil.showToast(context, "Please fill in all fields")
                        } else {
                            // Set loading state to true when the sign-up process starts.
                            isLoading = true
                            // Call the signup function from the AuthViewModel.
                            authViewModel.signup(email, name, password) { success, errorMessage ->
                                // Callback after the signup process completes.
                                if (success) {
                                    // Reset the loading state.
                                    isLoading = false
                                    // Navigate to the "home" screen after successful sign-up, clearing the back stack.
                                    navController.navigate("home") {
                                        popUpTo("auth") {//clearing backstack
                                            inclusive = true
                                        }
                                    }
                                } else {
                                    // Reset the loading state.
                                    isLoading = false
                                    // Display an error message if sign-up fails.
                                    AppUtil.showToast(context, errorMessage ?: "Something went wrong")
                                }
                            }
                        }
                    },
                    // Disable the button when loading is true to prevent multiple clicks.
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxSize() // Make the button fill the entire box.
                        .height(60.dp),
                    // Make the button's background transparent to show the Box's gradient.
                    colors = ButtonDefaults
                        .buttonColors(containerColor = Color.Transparent),
                    // Add a slight elevation to the button.
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                ) {
                    // Display "Creating Account..." while loading, otherwise display "Create Account".
                    Text(
                        text = if (isLoading) "Creating Account..." else "Create Account",
                        fontSize = 22.sp,
                        color = Color.White
                    )
                }


            }
            // Text button for navigating to the terms and conditions screen (currently navigates to signup).
            TextButton(onClick = {
                navController.navigate("signup") // Navigate to the "signup" screen.
            }) {
                Text(text = "Terms and Conditions")
            }

        }

    }
}
