package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.model.PlayersModel

/**
 * A composable function that displays a single player item in a card format.
 * Clicking the card navigates to the player details page.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 * @param player The [PlayersModel] object containing the data for the player.
 */
@Composable
fun PlayerItemView(modifier: Modifier = Modifier, player: PlayersModel) {

    // A Card composable displays information in a visually distinct and elevated container.
    Card(
        modifier = modifier
            .padding(8.dp) // Adds padding around the card.
            .clickable {
                // Navigates to the "player-details" screen when the card is clicked,
                // passing the player ID as a navigation argument.
                GlobalNavigation.navController.navigate("player-details/" + player.id)
            }
            .fillMaxWidth(), // Makes the card take up the full width of its parent.
        shape = RoundedCornerShape(16.dp), // Sets the shape of the card with rounded corners.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // Sets the background color of the card.
        elevation = CardDefaults.cardElevation(8.dp), // Adds a shadow to the card, giving it an elevated appearance.
    ) {
        // A Row composable arranges its children in a horizontal sequence.
        Row(
            modifier = Modifier
                .padding(12.dp) // Adds padding inside the Row within the Card.
                .fillMaxWidth() // Makes the Row take up the full width of its parent.
        ) {
            // Displays an image asynchronously from the player's image URL.
            AsyncImage(
                model = player.imageUrl, // The image URL of the player.
                contentDescription = "Image of ${player.fullName}", // Provides a content description for accessibility.
                modifier = Modifier
                    .height(120.dp) // Sets a fixed height for the image.
                    .width(120.dp) // Sets a fixed width for the image.
            )
            // A Column composable arranges its children in a vertical sequence.
            Column {
                // Displays the full name of the player.
                Text(
                    text = player.fullName,
                    modifier = Modifier.padding(8.dp), // Adds padding around the player's name.
                    fontWeight = FontWeight.SemiBold, // Makes the text semi-bold.
                    fontSize = 24.sp // Sets the font size to 24sp.
                )

            }
        }
    }
}
