package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.Image // Composable for displaying images.
import androidx.compose.foundation.layout.* // Imports various layout modifiers and composables (Column, Row, Spacer, etc.).
import androidx.compose.foundation.shape.CircleShape // Defines a circular shape.
import androidx.compose.material.icons.Icons // Access to standard Material Design Icons.
import androidx.compose.material.icons.filled.* // Imports specific filled Material Design Icons.
import androidx.compose.material3.* // Imports various Material Design 3 components (Card, Button, Text, Icon, etc.).
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.runtime.LaunchedEffect // A side-effect that runs when the composable enters composition. Useful for asynchronous operations like data fetching.
import androidx.compose.runtime.getValue // Delegate property for accessing the value of a state object.
import androidx.compose.runtime.mutableStateOf // Creates a mutable state object.
import androidx.compose.runtime.remember // Remembers a value across recompositions. Used here to retain state objects.
import androidx.compose.runtime.setValue // Delegate property for setting the value of a state object.
import androidx.compose.ui.Alignment // Defines alignment options for layout children.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.draw.clip // Modifier to clip the composable's content to a specific shape.
import androidx.compose.ui.graphics.Color // Represents a color.
import androidx.compose.ui.graphics.vector.ImageVector // Represents an icon graphic.
import androidx.compose.ui.layout.ContentScale // Defines how an image should be scaled.
import androidx.compose.ui.res.painterResource // Helper function to load drawable resources as a Painter.
import androidx.compose.ui.text.font.FontWeight // Defines font weight.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.
import androidx.compose.ui.unit.sp // Dimension unit representing scaled pixels for text size.

// Import resource identifiers (like drawable IDs)
import com.example.namibiahockeyunionapp.R // Imports the generated resource file.

// Import Firebase Authentication
import com.google.firebase.auth.FirebaseAuth // Imports Firebase Authentication instance.
import com.google.firebase.firestore.ktx.firestore // Imports Firebase Firestore with Kotlin extensions.
import com.google.firebase.ktx.Firebase // Imports the main Firebase object with Kotlin extensions.

/**
 * Composable function representing the user's profile page.
 * It displays the user's profile picture, name, email, and other details fetched from Firestore.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 */
@Composable
fun ProfilePage(modifier: Modifier = Modifier) {

    // State variable to hold the user's name.
    // `remember` retains the state across recompositions.
    // `mutableStateOf` makes the state observable.
    // `by` allows direct read/write access to the state value.
    var name by remember {
        mutableStateOf("") // Initialize with an empty string.
    }

    // State variable to hold the user's email.
    // Similar to the 'name' state, retained and observable.
    var email by remember {
        mutableStateOf("") // Initialize with an empty string.
    }

    // LaunchedEffect is used for side effects that should run within the composition.
    // key1 = Unit ensures this effect runs only once when the composable first enters composition.
    // This effect fetches user data (intended for name).
    LaunchedEffect(Unit) {
        // Access Firebase Firestore.
        Firebase.firestore
            // Navigate to the "users" collection.
            .collection("users")
            // Get the document corresponding to the current authenticated user's UID.
            // `FirebaseAuth.getInstance().currentUser?.uid!!` gets the current user's ID (!! assumes it's not null).
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            // Get the document content. Asynchronous operation.
            .get().addOnSuccessListener() {
                // Listener for successful data retrieval.
                // Update the 'name' state variable.
                // NOTE: This line seems to be fetching the "email" field from Firestore and setting it as the 'name' state.
                name = it.get("email").toString() // Potential bug: Should this be "name"?
            }
        // TODO: Add onFailureListener to handle errors during data fetching.
    }

    // LaunchedEffect is used for side effects that should run within the composition.
    // key1 = Unit ensures this effect runs only once when the composable first enters composition.
    // This effect fetches user data again (intended for email).
    LaunchedEffect(Unit) {
        // Access Firebase Firestore.
        // This is fetching the *same document* again. It's more efficient to fetch it once
        // and extract both 'name' and 'email' from the single result.
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnSuccessListener() {
                // Listener for successful data retrieval.
                // Update the 'email' state variable.
                // NOTE: This line seems to be fetching the "name" field from Firestore and setting it as the 'email' state.
                email = it.get("name").toString() // Potential bug: Should this be "email"? Fields seem swapped.
            }
        // TODO: Add onFailureListener to handle errors during data fetching.
    }


    // Main Column layout to arrange profile sections vertically.
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the column fill the entire screen.
            .padding(16.dp), // Add padding around the content.
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally within the column.
    ) {
        // Card composable to visually group the profile header elements.
        Card(
            modifier = Modifier.fillMaxWidth(), // Make the card fill the width.
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Add a shadow for visual separation.
        ) {
            // Column inside the card for arranging header elements vertically.
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Make this column fill the card's width.
                    .padding(16.dp), // Add padding inside the card.
                horizontalAlignment = Alignment.CenterHorizontally // Center elements horizontally within this inner column.
            ) {
                // Display the profile picture.
                Image(
                    painter = painterResource(id = R.drawable.profile), // Load image from drawable resources.
                    contentDescription = "Profile Picture", // Accessibility description.
                    modifier = Modifier
                        .size(100.dp) // Set the size of the image.
                        .clip(CircleShape), // Clip the image to a circular shape.
                    contentScale = ContentScale.Crop // Scale the image to fill the bounds, cropping if necessary.
                )

                // Add vertical space.
                Spacer(modifier = Modifier.height(16.dp))

                // Display user details (Name, Email, Member Type).
                // Note: 'name' and 'email' state variables are used here, which might hold swapped data based on the LaunchedEffect logic.
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 20.sp) // Display name.
                Text(text = email, color = Color.Black) // Display email.
                Text(text = "Player", color = MaterialTheme.colorScheme.primary) // Display static member type.

                // Add vertical space.
                Spacer(modifier = Modifier.height(16.dp))

                // Button for editing the profile.
                Button(
                    onClick = { /* TODO: Implement edit profile action */ }, // Placeholder click action.
                    modifier = Modifier.fillMaxWidth() // Make the button fill the width.
                ) {
                    // Icon and Text inside the button.
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Profile") // Edit icon.
                    Spacer(modifier = Modifier.width(8.dp)) // Horizontal space between icon and text.
                    Text(text = "Edit Profile") // Button text.
                }
            }
        }

        // Add vertical space after the card.
        Spacer(modifier = Modifier.height(24.dp))

        // Title for the additional information section.
        Text(text = "Account Information", style = MaterialTheme.typography.headlineSmall)
        // Add vertical space.
        Spacer(modifier = Modifier.height(8.dp))

        // Display various profile information using the helper composable ProfileInfoRow.
        // Note: 'name' and 'email' state variables are used, potentially with swapped data.
        // Other values are hardcoded strings.
        ProfileInfoRow(icon = Icons.Filled.Person, label = "Name", value = name) // Display Name row.
        ProfileInfoRow(icon = Icons.Filled.Email, label = "Email", value = email) // Display Email row.
        ProfileInfoRow(icon = Icons.Filled.Star, label = "Member Type", value = "Player") // Display Member Type row (hardcoded).
        ProfileInfoRow(icon = Icons.Filled.LocationOn, label = "Location", value = "Windhoek, Namibia") // Display Location row (hardcoded).
        ProfileInfoRow(icon = Icons.Filled.Phone, label = "Phone", value = "+264 81 888 5878") // Display Phone row (hardcoded).
        ProfileInfoRow(icon = Icons.Filled.DateRange, label = "Date of Birth", value = "1990-05-15") // Display Date of Birth row (hardcoded).

        // Add vertical space at the bottom.
        Spacer(modifier = Modifier.height(24.dp))

        // TODO: Consider making the main column scrollable if the content might exceed the screen height.
        // TODO: Implement the edit profile action.
        // TODO: Fetch 'name' and 'email' correctly and efficiently (one fetch, correct field assignment).
        // TODO: Ideally, fetch all dynamic details (Location, Phone, DOB) from Firestore as well.
    }
}

/**
 * Helper Composable function to display a single row of profile information with an icon, label, and value.
 *
 * @param icon The icon to display on the left.
 * @param label The label text (e.g., "Email").
 * @param value The value text (e.g., the user's email address).
 */
@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    // Row layout to arrange icon, label, and value horizontally.
    Row(
        modifier = Modifier
            .fillMaxWidth() // Make the row fill the width.
            .padding(vertical = 4.dp), // Add vertical padding around each row.
        verticalAlignment = Alignment.CenterVertically // Vertically align items in the center of the row.
    ) {
        // Display the icon.
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(24.dp)) // Set icon size.
        // Add horizontal space between the icon and the text.
        Spacer(modifier = Modifier.width(16.dp))
        // Display the label text.
        Text(
            text = "$label:", // Append a colon to the label.
            fontWeight = FontWeight.Bold, // Make the label text bold.
            modifier = Modifier.weight(1f) // Give this text a relative weight of 1, allowing it to take up part of the available space.
        )
        // Display the value text.
        Text(
            text = value,
            modifier = Modifier.weight(2f) // Give this text a relative weight of 2, allowing it to take up twice the space of the label text.
        )
    }
}
