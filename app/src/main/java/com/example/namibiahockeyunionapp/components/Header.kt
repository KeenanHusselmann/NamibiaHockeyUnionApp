package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * A composable function that displays the header of a screen, showing a welcome message
 * with the user's email and a logout button.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 */
@Composable
fun Header(modifier: Modifier = Modifier) {

    // State to hold the user's email address, initialized as an empty string.
    var name by remember {
        mutableStateOf("")
    }

    // LaunchedEffect is used to perform side effects that should run outside the scope of composition.
    // The Unit key ensures this effect runs only once when the composable is first created.
    LaunchedEffect(Unit) {
        // Accesses the Firestore database instance.
        Firebase.firestore.collection("users")
            // Navigates to the document corresponding to the currently logged-in user's UID.
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            // Retrieves the document data asynchronously.
            .get().addOnSuccessListener { documentSnapshot ->
                // This lambda is executed if the document is successfully retrieved.
                // It retrieves the value of the "email" field from the document and updates the 'name' state.
                name = documentSnapshot.get("email").toString()
            }
    }

    // A Row composable arranges its children in a horizontal sequence.
    Row(
        modifier = modifier
            .fillMaxWidth() // Makes the Row take up the full width of its parent.
            .padding(16.dp), // Adds padding of 16dp around the content of the Row.
        verticalAlignment = Alignment.CenterVertically, // Vertically aligns the items in the center.
        horizontalArrangement = Arrangement.SpaceBetween // Distributes the space evenly between the items.
    ) {
        // A Column composable arranges its children in a vertical sequence.
        Column {
            // Displays the "Welcome back" text with specific styling.
            Text(
                text = "Welcome back",
                fontFamily = FontFamily.Serif, // Uses a serif font family.
                fontWeight = FontWeight.Bold, // Makes the text bold.
                fontSize = 22.sp // Sets the font size to 22sp.
            )
            // Displays the user's email address with specific styling.
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 20.sp, // Sets the font size to 20sp.
                    fontWeight = FontWeight.Bold // Makes the text bold.
                )
            )
        }
        // An IconButton is a clickable icon that provides visual feedback when pressed.
        IconButton(onClick = {
            // This lambda is executed when the logout button is clicked.

            // Signs the current user out using Firebase Authentication.
            Firebase.auth.signOut()
            // Navigates to the "auth" screen after logging out.
            navController.navigate("auth") {
                // Clears the back stack up to the "home" screen (inclusive),
                // so the user cannot navigate back to the logged-in state.
                popUpTo("home") { inclusive = true }
            }
        }) {
            // Displays the ExitToApp icon as the visual representation of the button.
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout") 
        }
    }
}
