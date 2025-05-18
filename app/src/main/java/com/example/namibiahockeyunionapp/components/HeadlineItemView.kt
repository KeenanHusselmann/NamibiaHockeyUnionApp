package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.model.HeadlinesModel

/**
 * A composable function that displays a single headline item in a card format.
 * It includes an image, headline text, date, and a share button.
 * Clicking the card navigates to the news details page.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 * @param headline The [HeadlinesModel] object containing the data for the headline.
 */
@Composable
fun HeadlineItemView(modifier: Modifier = Modifier, headline: HeadlinesModel) {

    // A Card composable displays information in a visually distinct and elevated container.
    Card(
        modifier = modifier
            .fillMaxWidth() // Makes the card take up the full width of its parent.
            .padding(8.dp) // Adds padding around the card.
            .clickable {
                // Navigates to the "news-details" screen when the card is clicked,
                // passing the headline ID as a navigation argument.
                GlobalNavigation.navController.navigate("news-details/" + headline.id)
            },
        shape = RoundedCornerShape(16.dp), // Sets the shape of the card with rounded corners.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Sets the background color of the card.
        elevation = CardDefaults.cardElevation(8.dp) // Adds a shadow to the card, giving it an elevated appearance.
    ) {

        // A Column composable arranges its children in a vertical sequence.
        Column(
            modifier = Modifier.padding(12.dp) // Adds padding inside the Column within the Card.
        ) {

            // Displays an image asynchronously from the first URL in the headline's images list.
            AsyncImage(
                model = headline.images.firstOrNull(), // Gets the first image URL or null if the list is empty.
                contentDescription = headline.headline, // Provides a content description for accessibility.
                modifier = Modifier
                    .height(150.dp) // Sets a fixed height for the image.
                    .fillMaxSize() // Makes the image fill the available space within its height constraint.
            )
            // Displays the headline text.
            Text(
                text = headline.headline,
                fontWeight = FontWeight.SemiBold, // Makes the text semi-bold.
                maxLines = 4, // Limits the text to a maximum of 4 lines.
                overflow = TextOverflow.Ellipsis, // If the text exceeds the maximum lines, it will be truncated with an ellipsis.
                modifier = Modifier.padding(8.dp) // Adds padding around the headline text.
            )

            // A Row composable arranges its children in a horizontal sequence.
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Makes the Row take up the full width of its parent.
                    .padding(8.dp), // Adds padding around the content of the Row.
                verticalAlignment = Alignment.CenterVertically // Vertically aligns the items in the center.
            ) {
                // Displays the date of the headline.
                Text(
                    text = headline.date,
                    fontSize = 12.sp // Sets the font size to 12sp.
                )
                // A Spacer that takes up all the available weight, pushing the following elements to the end.
                Spacer(modifier = Modifier.weight(1f))

                // An IconButton is a clickable icon that provides visual feedback when pressed.
                IconButton(onClick = {

                }) {
                    // Displays the Share icon.
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share" // Provides a content description for accessibility.
                    )
                }
            }
        }
    }
}
