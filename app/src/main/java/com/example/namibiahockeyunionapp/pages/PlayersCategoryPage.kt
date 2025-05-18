package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.foundation.lazy.LazyColumn // A composable that displays a vertically scrolling list of items. Efficient for long lists.
import androidx.compose.foundation.lazy.items // Extension function for LazyColumn to easily render a list of items.
import androidx.compose.material3.Text // A Material Design text composable (imported but not used directly in the layout).
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.runtime.LaunchedEffect // A side-effect that runs only once when the composable enters composition (or key changes). Useful for asynchronous operations like data fetching.
import androidx.compose.runtime.mutableStateOf // Creates a mutable state object that can trigger recomposition when its value changes.
import androidx.compose.runtime.remember // Remembers a value across recompositions. Used here to retain the state object.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.

// Import custom composables and data models
import com.example.namibiahockeyunionapp.components.PlayerItemView // Imports the custom Composable for displaying a single player item.
import com.example.namibiahockeyunionapp.model.PlayerCategoryModel // Imports a data model class for player categories (imported but not directly used in this composable's logic).
import com.example.namibiahockeyunionapp.model.PlayersModel // Imports the data model class for a player.

// Import Firebase Firestore components
import com.google.firebase.Firebase // Imports the main Firebase object.
import com.google.firebase.firestore.firestore // Imports the Firebase Firestore instance.

/**
 * Composable function that displays a list of players fetched from Firestore
 * based on a given player category ID.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 * @param playerCategoryId The ID of the player category to filter players by.
 */
@Composable
fun PlayersCategoryPage(modifier: Modifier = Modifier, playerCategoryId: String) {
    // State variable to hold the list of players fetched from Firestore.
    // `remember` ensures this state is retained across recompositions.
    // `mutableStateOf` makes the list observable, so changes trigger recomposition.
    val playersList = remember {
        mutableStateOf<List<PlayersModel>>(emptyList()) // Initialize with an empty list.
    }

    // LaunchedEffect is used for side effects that should run within the composition.
    // key1 = Unit means this effect will run only once when the composable first enters composition.
    // This is suitable for one-time data fetching.
    LaunchedEffect(key1 = Unit) {
        // Access the Firebase Firestore database.
        Firebase.firestore
            // Navigate to the "data" collection.
            .collection("data")
            // Access the document named "players".
            .document("players")
            // Navigate to the "player" subcollection within the "players" document.
            .collection("player")
            // Filter the documents in the "player" collection where the field "category"
            // is equal to the provided playerCategoryId.
            .whereEqualTo("category", playerCategoryId)
            // Get the documents that match the query. This is an asynchronous operation.
            .get().addOnCompleteListener() {
                // Add a listener to handle the result of the asynchronous operation.
                // Check if the data fetch was successful.
                if (it.isSuccessful) {
                    // If successful, process the results.
                    val resultList = it.result.documents.mapNotNull { doc ->
                        // For each document, try to convert it into a PlayersModel object.
                        // mapNotNull filters out any documents that cannot be converted.
                        doc.toObject(PlayersModel::class.java)
                    }
                    // Update the state variable `playersList` with the fetched list of players.
                    // The `.plus(resultList)` chain appears to be intentionally duplicating the fetched data
                    // multiple times. This might be done for testing purposes to simulate a long list
                    // and observe the performance of LazyColumn.
                    playersList.value = resultList
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList)
                        .plus(resultList) // Data is duplicated 10 times here.
                }
                // TODO: Add error handling here (e.g., log the error, show an error message).
            }
    }

    // LazyColumn is an efficient way to display a scrollable list of items.
    // It only renders the items that are currently visible on the screen.
    LazyColumn(
        // Modifiers applied to the LazyColumn.
        modifier = modifier
            .fillMaxSize() // Make the LazyColumn fill the available space.
            .padding(16.dp) // Add padding around the list items.
    ){
        // Use the `items` extension function to iterate over the list of players.
        // For each `item` (which is a PlayersModel object) in `playersList.value`,
        // render the content defined in the lambda.
        items(playersList.value){item->
            // Call the custom PlayerItemView composable for each player item.
            // This composable is responsible for rendering the UI for a single player.
            PlayerItemView( modifier, item) // Pass the external modifier and the current player item.
        }
    }
}
