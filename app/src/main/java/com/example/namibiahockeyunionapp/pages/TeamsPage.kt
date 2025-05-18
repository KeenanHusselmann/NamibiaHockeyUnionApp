package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.Column // Used for arranging UI elements vertically.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.foundation.lazy.LazyColumn // A composable that displays a vertically scrolling list of items. Efficient for long lists.
import androidx.compose.foundation.lazy.items // Extension function for LazyColumn to easily render a list of items.
import androidx.compose.material3.Text // A Material Design text composable for displaying text.
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.runtime.DisposableEffect // A side-effect that requires cleanup when the composable leaves composition. Ideal for listeners.
import androidx.compose.runtime.LaunchedEffect // A side-effect that runs when the composable enters composition (imported but DisposableEffect is used for data fetching).
import androidx.compose.runtime.mutableStateOf // Creates a mutable state object.
import androidx.compose.runtime.remember // Remembers a value across recompositions. Used here to retain the state object.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.text.TextStyle // Defines styling for text.
import androidx.compose.ui.text.font.FontWeight // Defines font weight.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.
import androidx.compose.ui.unit.sp // Dimension unit representing scaled pixels for text size.

// Import custom composables and data models
import com.example.namibiahockeyunionapp.components.TeamsView // Imports the custom Composable for displaying a single team member item.
import com.example.namibiahockeyunionapp.model.UserModel // Imports the data model class for a user, expected to contain team information.

// Import Firebase components
import com.google.firebase.Firebase // Imports the main Firebase object.
import com.google.firebase.auth.FirebaseAuth // Imports Firebase Authentication instance.
import com.google.firebase.firestore.firestore // Imports Firebase Firestore instance.

/**
 * Composable function representing the page that displays the user's team members.
 * It fetches the team data in real-time from the current user's document in Firestore.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 * @param categoryId A string identifier, currently unused in this composable.
 * @param playerId A string identifier, currently unused in this composable (note: there's also a playerId in the team list).
 * @param qty A Long value, currently unused in this composable.
 */
@Composable
fun TeamsPage(modifier: Modifier = Modifier, categoryId: String, playerId: String, qty: Long ) {

    // State variable to hold the UserModel data fetched from Firestore.
    // Initialized with a default UserModel instance.
    val userModel = remember {
        mutableStateOf(UserModel())
    }

    // DisposableEffect is used to manage side effects that require cleanup.
    // Here, it's used to set up a real-time Firestore snapshot listener.
    // key1 = Unit ensures this effect runs once when the composable enters composition.
    DisposableEffect(key1 = Unit) {
        // Add a snapshot listener to the current user's document in the "users" collection.
        // This listener will be triggered every time the document changes.
      var listener =   Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!) // Get the current user's document. !! assumes UID is not null.
            .addSnapshotListener{it, _ -> // Lambda that receives the snapshot and potential error.
                // Check if the snapshot is not null.
                if(it != null){
                    // Try to convert the document snapshot to a UserModel object.
                    val result = it.toObject(UserModel::class.java)
                    // If the conversion was successful (result is not null).
                    if (result!= null) {
                        // Update the userModel state variable with the new data.
                        // This triggers recomposition, updating the UI with the latest team data.
                        userModel.value = result
                    }
                }
                // TODO: Handle the error case (the second parameter in the lambda, currently ignored `_`).
            }
        // The `onDispose` block is called when the composable leaves the composition.
        // This is crucial for removing the snapshot listener to prevent memory leaks.
        onDispose {
            listener.remove() // Remove the Firestore snapshot listener.
        }
    }

    // Main Column layout to arrange UI elements vertically.
    Column(
        modifier = modifier
            .fillMaxSize() // Make the column fill the entire screen size.
            .padding(16.dp), // Add padding around the content.
    )
    {
        // Display a static title for the team page.
        Text(text = "Men's Inline Team A", // Note: This title is static and doesn't reflect the actual team name from userModel.
            style = TextStyle(
                fontSize = 24.sp, // Set font size.
                fontWeight = FontWeight.SemiBold // Set font weight.
            ))

        // LazyColumn to efficiently display the list of team players.
        // It only renders visible items.
        LazyColumn{
            // Use the `items` extension function to iterate over the team players.
            // `userModel.value.teamPlayers` is expected to be a Map<String, Long> where key is playerId and value is quantity.
            // `.toList()` converts the map entries into a List<Map.Entry<String, Long>>, which `items` can process.
            items(userModel.value.teamPlayers.toList(), // Data source for the list.
                key = {it.first}) // Use the player ID (the map key, which is the first element of the pair) as the unique key for each item. Important for efficient list updates.
            {(playerId,qty)-> // Lambda providing the key-value pair (playerId and quantity) for each item.
                // Call the custom TeamsView composable for each team player.
                // This composable is responsible for displaying the UI for a single team member.
                TeamsView(modifier = modifier, playerId = playerId, qty = qty) // Pass the external modifier and the specific player ID and quantity.
            }
      }
    }

    // Note: The parameters 'categoryId', 'playerId' (page parameter), and 'qty' (page parameter)
    // are passed to this Composable but are not used within its current logic.
    // They might be intended for future features or were part of a previous implementation.
}
