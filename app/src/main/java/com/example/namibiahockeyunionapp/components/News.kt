package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.model.NewsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A composable function that displays a horizontal list of news categories.
 * It fetches the news categories from Firebase Firestore.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 */
@Composable
fun News(modifier: Modifier = Modifier) {
    // State to hold the list of news categories fetched from Firebase.
    val newsList = remember {
        mutableStateOf<List<NewsModel>>(emptyList())
    }

    // LaunchedEffect is used to perform side effects that should run outside the scope of composition.
    // The Unit key ensures this effect runs only once when the composable is first created.
    LaunchedEffect(key1 = Unit) {
        // Accesses the Firestore database instance.
        Firebase.firestore.collection("data")
            // Navigates to the document named "news".
            .document("news")
            // Navigates to the sub-collection named "categories" within the "news" document.
            .collection("categories")
            // Retrieves all documents from the "categories" sub-collection.
            .get().addOnCompleteListener { task ->
                // This lambda is executed when the Firestore operation is complete.
                if (task.isSuccessful) {
                    // If the task was successful, map the documents to NewsModel objects.
                    val resultList = task.result.documents.mapNotNull { doc ->
                        doc.toObject(NewsModel::class.java)
                    }
                    // Update the newsList state with the fetched list of news categories.
                    newsList.value = resultList
                }
            }
    }
    // A LazyRow composable displays a horizontally scrolling list of items.
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp) // Adds a 20dp spacing between each item in the row.
    ) {
        // The items lambda defines how to display each item in the newsList.
        items(newsList.value) { item ->
            // For each NewsModel item, the NewsItem composable is called to display it.
            NewsItem(category = item)
        }
    }
}

/**
 * A composable function that displays a single news category item in a card format.
 * Clicking the card navigates to the news headlines page for the selected category.
 *
 * @param category The [NewsModel] object containing the data for the news category.
 */
@Composable
fun NewsItem(category: NewsModel) {
    // A Card composable displays information in a visually distinct and elevated container.
    Card(
        modifier = Modifier
            .size(150.dp) // Sets a fixed size for the card.
            .clickable {
                // Navigates to the "news-headlines" screen when the card is clicked,
                // passing the category ID as a navigation argument.
                GlobalNavigation.navController.navigate("news-headlines/" + category.id)
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

            // Displays the name of the news category, centered within the Column.
            Text(text = category.name, textAlign = TextAlign.Center)
            // Adds a small vertical space below the category name.
            Spacer(modifier = Modifier.height(4.dp))

            // Displays an image asynchronously from the category's image URL.
            AsyncImage(
                model = category.imageUrl, // The URL of the category's image.
                contentDescription = category.name, // Provides a content description for accessibility.
                modifier = Modifier.size(150.dp) // Sets a fixed size for the image (same as the Card).
            )
        }
    }
}
