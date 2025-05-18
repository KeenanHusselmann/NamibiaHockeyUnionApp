package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.example.namibiahockeyunionapp.model.PlayerCategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A composable function that displays a list of player categories.
 * It fetches the player categories from Firebase Firestore and displays them in a vertical scrolling list.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 */
@Composable
fun PlayerCategoryView(modifier: Modifier = Modifier) {

    // State to hold the list of player categories fetched from Firebase.
    val playerCategoryList = remember {
        mutableStateOf<List<PlayerCategoryModel>>(emptyList())
    }

    // LaunchedEffect is used to perform side effects that should run outside the scope of composition.
    // The Unit key ensures this effect runs only once when the composable is first created.
    LaunchedEffect(key1 = Unit) {
        // Accesses the Firestore database instance.
        Firebase.firestore.collection("data")
            // Navigates to the document named "players".
            .document("players")
            // Navigates to the sub-collection named "playerCategories" within the "players" document.
            .collection("playerCategories")
            // Retrieves all documents from the "playerCategories" sub-collection.
            .get().addOnCompleteListener { task ->
                // This lambda is executed when the Firestore operation is complete.
                if (task.isSuccessful) {
                    // If the task was successful, map the documents to PlayerCategoryModel objects.
                    val resultList = task.result.documents.mapNotNull { doc ->
                        doc.toObject(PlayerCategoryModel::class.java)
                    }
                    // Update the playerCategoryList state with the fetched list of player categories.
                    playerCategoryList.value = resultList
                }
            }
    }

    // A LazyColumn composable displays a vertically scrolling list of items.
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Makes the LazyColumn fill the entire available screen space.
        horizontalAlignment = Alignment.CenterHorizontally, // Centers the items horizontally within the column.
        verticalArrangement = Arrangement.Center // Centers the items vertically within the column.
    ) {
        // The items lambda defines how to display each item in the playerCategoryList.
        items(playerCategoryList.value) { item ->
            // For each PlayerCategoryModel item, a Box is used to center the PlayerCategoryItem.
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                // Calls the PlayerCategoryItem composable to display a single player category.
                PlayerCategoryItem(playerCategory = item)
            }
        }
    }
}

/**
 * A composable function that displays a single player category item in a card format.
 * Clicking the card navigates to the players list page for the selected category.
 *
 * @param playerCategory The [PlayerCategoryModel] object containing the data for the player category.
 */
@Composable
fun PlayerCategoryItem(playerCategory: PlayerCategoryModel) {

    // A Card composable displays information in a visually distinct and elevated container.
    Card(
        modifier = Modifier
            .size(300.dp) // Sets a fixed size for the card.
            .padding(16.dp) // Adds padding around the card.
            .clickable {
                // Navigates to the "players-category" screen when the card is clicked,
                // passing the player category ID as a navigation argument.
                GlobalNavigation.navController.navigate("players-category/" + playerCategory.id)
            },
        shape = RoundedCornerShape(16.dp), // Sets the shape of the card with rounded corners.
        elevation = CardDefaults.cardElevation(4.dp), // Adds a small shadow to the card.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Sets the background color of the card.
    ) {

        // A Column composable arranges its children in a vertical sequence.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centers the items horizontally within the Column.
            verticalArrangement = Arrangement.Center, // Centers the items vertically within the Column.
            modifier = Modifier.fillMaxSize() // Makes the Column fill the entire space of the Card.
        ) {
            // Displays the name of the player category with specific styling.
            Text(
                text = playerCategory.name,
                textAlign = TextAlign.Center, // Centers the text within its container.
                fontSize = 20.sp, // Sets the font size to 20sp.
                fontWeight = FontWeight.Bold, // Makes the text bold.
                fontFamily = FontFamily.SansSerif // Uses a sans-serif font family.
            )
            // Displays an image asynchronously from the player category's image URL.
            AsyncImage(
                model = playerCategory.imageUrl, // The URL of the player category's image.
                contentDescription = "Image of ${playerCategory.name}", // Provides a content description for accessibility.
                modifier = Modifier.size(200.dp) // Sets a fixed size for the image.
            )

            // Adds a small vertical space below the image.
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
