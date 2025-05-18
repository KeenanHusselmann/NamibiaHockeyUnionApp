package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.Column // Used for arranging UI elements vertically.
import androidx.compose.foundation.layout.Row // Used for arranging UI elements horizontally.
import androidx.compose.foundation.layout.Spacer // Used to add empty space between UI elements.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.fillMaxWidth // Modifier to make a composable fill the maximum width available.
import androidx.compose.foundation.layout.height // Modifier to set the height of a composable.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.foundation.layout.width // Modifier to set the width of a composable.
// import androidx.compose.foundation.pager.HorizontalPager // Imported but not used in this composable. Likely for a pager view.
// import androidx.compose.foundation.pager.rememberPagerState // Imported but not used. Related to Pager.
import androidx.compose.foundation.rememberScrollState // Remembers the scroll state for a scrollable container.
import androidx.compose.foundation.shape.RoundedCornerShape // Defines a shape with rounded corners.
import androidx.compose.foundation.verticalScroll // Modifier to make a composable vertically scrollable.
import androidx.compose.material3.Button // A Material Design button composable.
import androidx.compose.material3.MaterialTheme // Provides access to the Material Design theme properties (not directly used here).
import androidx.compose.material3.Text // A Material Design text composable.
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.runtime.LaunchedEffect // A side-effect that runs only once when the composable enters composition (or key changes). Useful for asynchronous operations like data fetching.
import androidx.compose.runtime.getValue // Delegate property for accessing the value of a state object.
import androidx.compose.runtime.mutableStateOf // Creates a mutable state object.
import androidx.compose.runtime.remember // Remembers a value across recompositions. Used here to retain the state object.
import androidx.compose.runtime.setValue // Delegate property for setting the value of a state object.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.draw.clip // Modifier to clip the composable's content to a specific shape.
import androidx.compose.ui.platform.LocalContext // Provides access to the current Android Context.
import androidx.compose.ui.text.font.FontWeight // Defines the thickness of the font glyphs.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.
import androidx.compose.ui.unit.sp // Dimension unit representing scaled pixels for text size.

// Import external libraries for image loading and potentially dots indicator (though unused)
import coil.compose.AsyncImage // Composable from Coil library for loading images asynchronously by URL.
import com.example.namibiahockeyunionapp.AppUtil // Imports a utility class, likely containing helper functions like addPlayerToTeam.
import com.example.namibiahockeyunionapp.GlobalNavigation.navController // Imports the globally defined NavController for navigation (not used in this snippet).
import com.example.namibiahockeyunionapp.model.PlayersModel // Imports the data model class for a player.

// Import Firebase Firestore components
import com.google.firebase.Firebase // Imports the main Firebase object.
import com.google.firebase.firestore.firestore // Imports the Firebase Firestore instance.

// Imports for view pager dots indicator (imported but not used in this composable)
// import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
// import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
// import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

/**
 * Composable function that displays the detailed information for a specific player
 * fetched from Firebase Firestore.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 * @param playerId The ID of the player whose details are to be displayed.
 */
@Composable
fun PlayerDetailsPage(modifier: Modifier = Modifier, playerId: String) {

    // State variable to hold the fetched player data.
    // `remember` ensures the state is retained across recompositions.
    // `mutableStateOf` creates an observable state.
    // `by` enables easy read/write access to the state value using `player = ...`.
    var player by remember {
        mutableStateOf<PlayersModel?>(null) // Initialize with null, as data is fetched asynchronously.
    }

    // Get the current Android context. Useful for operations like showing Toasts or accessing resources.
    var context = LocalContext.current

    // LaunchedEffect is used for side effects like data fetching that should occur within the composition.
    // key1 = Unit ensures this effect runs only once when the composable enters composition.
    LaunchedEffect(key1 = Unit) {
        // Access the Firebase Firestore database.
        Firebase.firestore
            // Navigate to the "data" collection.
            .collection("data")
            // Access the document named "players".
            .document("players")
            // Navigate to the "player" subcollection within the "players" document.
            .collection("player")
            // Access the specific document whose ID matches the provided playerId.
            .document(playerId)
            // Get the single document. This is an asynchronous operation.
            .get()
            // Add a listener to handle the result of the asynchronous operation.
            .addOnCompleteListener {
                // Check if the data fetch was successful.
                if (it.isSuccessful) {
                    // If successful, get the document snapshot result.
                    var result = it.result.toObject(PlayersModel::class.java) // Try to convert the document to a PlayersModel object.
                    // Check if the conversion was successful (result is not null).
                    if (result != null) {
                        // Update the state variable `player` with the fetched player data.
                        // This update triggers a recomposition of the composable, causing the UI to update.
                        player = result
                    }
                }
                // TODO: Add error handling here (e.g., log the error, show an error message if fetching fails).
            }
    }

    // Main Column layout for arranging UI elements vertically.
    Column(
        modifier = modifier
            .fillMaxSize() // Make the column fill the entire available space.
            .padding(16.dp) // Add padding around the content.
            .verticalScroll(rememberScrollState()) // Make the column content vertically scrollable. `rememberScrollState` manages the scroll position.
    ) {
        // Display the player's full name.
        // Use the Elvis operator (?: "") to display an empty string if `player` or `fullName` is null while data is loading.
        Text(text = player?.fullName ?: "",
            fontWeight = FontWeight.SemiBold, // Set the text font weight.
            fontSize = 24.sp, // Set the text font size.
            modifier = Modifier.padding(8.dp) // Add padding around the text.
        )

        // Adds vertical space.
        Spacer(modifier = Modifier.height(16.dp))

        // Another Column, potentially to group the image and button.
        Column(

        ) {
            // Displays the player's image asynchronously using Coil.
            // `player?.imageUrl` safely accesses the image URL (null-safe).
            AsyncImage(model = player?.imageUrl,
                contentDescription = "Player image", // Provide a content description for accessibility.
                modifier = Modifier
                    .height(180.dp) // Set the image height.
                    .width(180.dp) // Set the image width.
                    .fillMaxWidth() // This modifier seems redundant or potentially conflicting with fixed height/width. It might stretch the image container if the content scale allows.
                    .clip(RoundedCornerShape(16.dp)) // Clip the image to a rounded corner shape.
            )

            // Adds vertical space.
            Spacer(modifier = Modifier.height(20.dp))

            // Button to add the player to a team.
            Button(
                // Defines the action when the button is clicked.
                onClick = {
                    // Call the `addPlayerToTeam` utility function, passing the context and player ID.
                    AppUtil.addPlayerToTeam(context = context, playerId = playerId)
                },
                // Modifiers for the button.
                modifier = Modifier
                    .fillMaxWidth() // Make the button fill the available width.
                    .height(34.dp) // Set the button height.
            ){
                // Text displayed inside the button.
                Text(text = "Add to Team", fontSize = 16.sp)
            }

            // Adds vertical space.
            Spacer(modifier = Modifier.height(20.dp))

            // Conditionally display the "Player Details" title.
            // It only shows if `player` is not null AND `player.otherDetails` is not null AND not empty.
            if(player?.otherDetails?.isNotEmpty() == true) {
                Text(text = "Player Details",
                    fontWeight = FontWeight.SemiBold, // Set font weight.
                    fontSize = 24.sp // Set font size.
                )
            }
            // Adds vertical space after the "Player Details" title.
            Spacer(modifier = Modifier.height(16.dp))

            // Iterate through the `otherDetails` map (if it exists and is not empty)
            // and display each key-value pair as a row.
            // The Elvis operator `?.forEach` safely handles the case where `player` or `otherDetails` is null.
            player?.otherDetails?.forEach { (key, value) ->
                // Row layout for displaying the key and value side-by-side.
                Row(
                    modifier = Modifier
                        .fillMaxWidth() // Make the row fill the available width.
                        .padding(8.dp) // Add padding around each detail row.
                ){
                    // Display the detail key followed by a colon.
                    Text(text = "$key : ", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    // Display the detail value.
                    Text(text = value, fontSize = 16.sp)
                }
            }
        }
    }
}
