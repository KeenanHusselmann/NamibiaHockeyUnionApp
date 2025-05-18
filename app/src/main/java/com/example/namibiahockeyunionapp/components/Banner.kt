package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

/**
 * A composable function that displays a banner with a horizontal image slider and dots indicator.
 * It fetches banner image URLs from Firebase Firestore.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 */
@Composable
fun Banner(modifier: Modifier = Modifier) {

    // State to hold the list of banner image URLs. It's initialized as an empty list
    // and will be updated when the data is fetched from Firebase.
    var bannerList by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    // LaunchedEffect is used to perform side effects that should run outside the scope of composition.
    // The Unit key ensures this effect runs only once when the composable is first created.
    LaunchedEffect(Unit) {
        // Accesses the Firestore database instance.
        Firebase.firestore.collection("data")
            // Navigates to the specific document named "banners" within the "data" collection.
            .document("banners")
            // Retrieves the data from the document asynchronously.
            .get().addOnCompleteListener { task ->
                // This lambda is executed when the Firestore operation is complete.
                if (task.isSuccessful) {
                    // If the task was successful, retrieve the document snapshot.
                    val document = task.result
                    if (document != null && document.exists()) {
                        // If the document exists, get the value of the field named "urls".
                        // It is cast to List<String> as we expect a list of image URLs.
                        bannerList = document.get("urls") as List<String>
                    }
                }
            }
    }

    // A Column composable arranges its children in a vertical sequence.
    Column(
        modifier = modifier
    ) {
        // Creates a PagerState for the HorizontalPager. It remembers the state across recompositions.
        // The initialPage is set to 0, and the pageCount is dynamically determined by the size of the bannerList.
        val pagerState = rememberPagerState(initialPage = 0) {
            bannerList.size
        }
        // A horizontally scrollable container that displays its individual pages using the provided PagerState.
        HorizontalPager(
            state = pagerState, // Associates the PagerState with the HorizontalPager.
            pageSpacing = 24.dp // Adds a 24dp spacing between each page in the pager.
        ) { page -> // Lambda that provides the index of the current page.
            // Displays an image asynchronously from the URL at the current page index in the bannerList.
            AsyncImage(
                model = bannerList.get(page), // The image URL for the current page.
                contentDescription = "Banner images", // A content description for accessibility.
                modifier = Modifier
                    .fillMaxWidth() // Makes the image fill the entire width of the HorizontalPager.
                    .clip(RoundedCornerShape(16.dp)) // Clips the image with rounded corners of 16dp.
            )
        }
        // Adds a vertical space of 10dp below the HorizontalPager.
        Spacer(modifier = Modifier.height(10.dp))

        // Displays dots to indicate the current page and the total number of pages in the HorizontalPager.
        DotsIndicator(
            dotCount = bannerList.size, // The total number of dots, which corresponds to the number of banners.
            type = ShiftIndicatorType( // Sets the visual style and animation of the dots indicator.
                DotGraphic(
                    color = MaterialTheme.colorScheme.primary, // Sets the color of the active dot to the primary theme color.
                    size = 6.dp // Sets the size of each dot to 6dp.
                )
            ),
            pagerState = pagerState // Associates the DotsIndicator with the PagerState to reflect the current page.
        )
    }
}
