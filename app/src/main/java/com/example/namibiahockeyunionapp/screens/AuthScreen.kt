package com.example.namibiahockeyunionapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavHostController
import com.ag_apps.googlesignin.GoogleAuthClient
import com.example.namibiahockeyunionapp.R
import kotlinx.coroutines.launch


@Composable
fun AuthScreen(modifier: Modifier = Modifier, navController: NavHostController){

    // Get the current context for displaying Toast messages.
    val context = LocalContext.current
    // Initialize the GoogleAuthClient using the current context. This client handles Google Sign-In functionality.
    val googleAuthClient by remember { mutableStateOf(GoogleAuthClient(context)) }
    // Create a CoroutineScope tied to the lifecycle of this composable. Used for launching asynchronous tasks.
    val scope = rememberCoroutineScope()
    // State to track if a sign-in process is currently in progress. Saved across recompositions.
    var isSignInInProgress by rememberSaveable { mutableStateOf(false) }

    // LaunchedEffect runs a side effect within the scope of the composable.
    // The key1 = Unit ensures it runs only once when the composable is first created.
    LaunchedEffect(key1 = Unit) {
        // Check if the user is already signed in with Google.
        if (googleAuthClient.isSingedIn()) {
            // If signed in, navigate to the "home" screen, removing the "auth" screen from the back stack.
            navController.navigate("home") {
                popUpTo("auth") {
                    inclusive = true // Include the "auth" screen in the pop-up.
                }
            }
        }
    }


    // Box composable to stack elements on top of each other, allowing for a background image.
    Box(modifier = Modifier.fillMaxSize()){
        // Display the background image, filling the entire screen and scaling to fit.
         Image( painter = painterResource(id = R.drawable.bg4), contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Column to arrange elements vertically in the center of the screen.
 Column (
        modifier = modifier
            .fillMaxWidth() // Make the column take the full width.
            .padding(16.dp) // Add some padding around the column.
        ,
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
        // Add vertical space between the logo and the "Welcome To" text.
        Spacer(modifier = Modifier.height(10.dp))

        // Display the "Welcome To" text with specific styling.
        Text(
            text = "Welcome To",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif, // Use a serif font.
                fontWeight = FontWeight.SemiBold, // Make the text semi-bold.
                textAlign = TextAlign.Center // Center the text.
            )
        )
        // Add vertical space.
        Spacer(modifier = Modifier.height(20.dp))

        // Display the application name with specific styling.
        Text(
            text = "Namibia Hockey App",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        )

        // Add vertical space before the login button.
        Spacer(modifier = Modifier.height(40.dp))

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
            // Button for navigating to the login screen.
            Button(
                onClick = {
                    navController.navigate("login") // Navigate to the "login" route.
                },
                modifier = Modifier.fillMaxSize(), // Make the button fill the entire box.
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Make the button's background transparent to show the box's gradient.
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp), // Add a slight elevation to the button.
            ) {
                // Text displayed on the Login button.
                Text(text = "Login",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray)
            }

        }
        // Add vertical space between the Login and Sign Up buttons.
        Spacer(modifier = Modifier.height(20.dp))
        // Box to create a rounded background with a gradient for the Sign Up button.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Blue, Color.Magenta)
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            // Button for navigating to the sign-up screen.
            Button(
                onClick = {
                    navController.navigate("signup") // Navigate to the "signup" route.
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent), // Make the button's background transparent.
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Ensure the button's container is transparent.
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp) // Add elevation to the button.
            ) {
                // Text displayed on the Sign Up button.
                Text(text = "Sign Up",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
        }


    }
        // Add vertical space before the "Or sign up with" section at the bottom.
        Spacer(modifier = Modifier.height(20.dp))
        // Column to arrange the "Or sign up with" text and social login icons at the bottom.
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align this column to the bottom center of the Box.
                .fillMaxWidth() // Make the column take the full width.
                .padding(bottom = 10.dp), // Add bottom padding.
            horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally.
        ) {


        // Text indicating social login options.
        Text(text = "Or sign up with",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,)

        // Row to arrange the social login icons horizontally with even spacing.
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribute space evenly between the icons.
        ) {
            // Facebook login icon. Currently shows a Toast message when clicked.
            Image(
                painter = painterResource(id = R.drawable.fb),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(40.dp) // Set the size of the icon.
                    .clickable { // Make the image clickable.
                        Toast.makeText(context, "Facebook login not implemented yet", Toast.LENGTH_SHORT).show()

                    }
            )
            // Google login icon. Handles Google Sign-In.
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(enabled = !isSignInInProgress) { // Disable click during sign-in.
                        isSignInInProgress = true // Set sign-in in progress to true.
                        scope.launch { // Launch a coroutine to perform the sign-in asynchronously.
                            val signedIn = googleAuthClient.signIn() // Initiate Google Sign-In.
                            isSignInInProgress = false // Reset the sign-in in progress state.
                            if (signedIn) {
                                Toast.makeText(
                                    context,
                                    "Google sign-in successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("home") { // Navigate to the "home" screen.
                                    popUpTo("auth") {
                                        inclusive = true // Remove the "auth" screen from the back stack.
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    }
            )
            // Apple login icon. Currently shows a Toast message when clicked.
            Image(
                painter = painterResource(id = R.drawable.apple),
                contentDescription = "Apple",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        Toast.makeText(context, "Apple login not implemented yet", Toast.LENGTH_SHORT).show()
                    }
            ) }
        }
    }
}
