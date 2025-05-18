package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.foundation.lazy.LazyColumn // A composable that displays a vertically scrolling list of items. Efficient for long lists.
import androidx.compose.foundation.lazy.items // Extension function for LazyColumn to easily render a list of items.
import androidx.compose.material3.Text // A Material Design text composable (although not used directly in the final layout, it's often imported).
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.runtime.LaunchedEffect // A side-effect that runs only once when the composable enters composition (or key changes). Useful for asynchronous operations like data fetching.
import androidx.compose.runtime.mutableStateOf // Creates a mutable state object that can trigger recomposition when its value changes.
import androidx.compose.runtime.remember // Remembers a value across recompositions. Used here to retain the state object.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.

// Import custom composables and data models
import com.example.namibiahockeyunionapp.components.HeadlineItemView // Imports the custom Composable for displaying a single headline item.
import com.example.namibiahockeyunionapp.model.HeadlinesModel // Imports the data model class for a headline.
import com.example.namibiahockeyunionapp.model.NewsModel // Imports the data model class for news (might be related but not directly used in this composable's state).

// Import Firebase Firestore components
import com.google.firebase.Firebase // Imports the main Firebase object.
import com.google.firebase.firestore.firestore // Imports the Firebase Firestore instance.

/**
 * Composable function that displays a list of news headlines fetched from Firestore
 * based on a given category ID.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 * @param categoryId The ID of the category to filter headlines by.
 */
@Composable
fun NewsHeadlinesPage(modifier: Modifier = Modifier, categoryId: String) {
    // State variable to hold the list of headlines fetched from Firestore.
    // `remember` ensures this state is retained across recompositions.
    // `mutableStateOf` makes the list observable, so changes trigger recomposition.
    val headlinesList = remember {
        mutableStateOf<List<HeadlinesModel>>(emptyList()) // Initialize with an empty list.
    }

    // LaunchedEffect is used for side effects that should run within the composition.
    // key1 = Unit means this effect will run only once when the composable first enters composition.
    // This is suitable for one-time data fetching.
    LaunchedEffect(key1 = Unit) {
        // Access the Firebase Firestore database.
        Firebase.firestore
            // Navigate to the "data" collection.
            .collection("data")
            // Access the document named "news".
            .document("news")
            // Navigate to the "headlines" subcollection within the "news" document.
            .collection("headlines")
            // Filter the documents in the "headlines" collection where the field "category"
            // is equal to the provided categoryId.
            .whereEqualTo("category", categoryId)
            // Get the documents that match the query. This is an asynchronous operation.
            .get()
            // Add a listener to handle the result of the asynchronous operation.
            .addOnCompleteListener {
                // Check if the data fetch was successful.
                if (it.isSuccessful) {
                    // If successful, process the results.
                    val resultList = it.result.documents.mapNotNull { doc ->
                        // For each document, try to convert it into a HeadlinesModel object.
                        // mapNotNull filters out any documents that cannot be converted.
                        doc.toObject(HeadlinesModel::class.java)
                    }
                    // Update the state variable `headlinesList` with the fetched list of headlines.
                    // This update triggers a recomposition of the composable, causing the UI to update.
                    headlinesList.value = resultList
                }
                // TODO: Add error handling here (e.g., log the error, show an error message).
            }
    }

    // LazyColumn is an efficient way to display a scrollable list of items.
    // It only renders the items that are currently visible on the screen.
    LazyColumn(
        // Modifiers applied to the LazyColumn.
        modifier = modifier // Apply any external modifiers passed to the composable.
            .fillMaxSize() // Make the LazyColumn fill the available space.
            .padding(16.dp) // Add padding around the list items.
    ) {
        // Use the `items` extension function to iterate over the list of headlines.
        // For each `item` (which is a HeadlinesModel object) in `headlinesList.value`,
        // render the content defined in the lambda.
        items(headlinesList.value) { item ->
            // Call the custom HeadlineItemView composable for each headline item.
            // This composable is responsible for rendering the UI for a single headline.
            HeadlineItemView(Modifier, item) // Pass a default Modifier and the current headline item.
        }
    }
}
