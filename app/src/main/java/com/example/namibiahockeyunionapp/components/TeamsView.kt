package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Delete
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.model.PlayersModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/**
 * A composable function that displays information about a player and the number of games they played.
 * It also includes a button to remove the player from a team.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 * @param playerId The ID of the player to display.
 * @param qty The number of games played by the player.
 */
@Composable
fun TeamsView(modifier: Modifier = Modifier, playerId: String, qty: Long) {

    // State to hold the PlayersModel object for the given playerId.
    var player by remember {
        mutableStateOf(PlayersModel())
    }

    // LaunchedEffect is used to perform side effects that should run outside the scope of composition.
    // The Unit key ensures this effect runs once when the composable is first created or when the playerId changes.
    LaunchedEffect(key1 = playerId) {
        // Accesses the Firestore database instance.
        Firebase.firestore.collection("data")
            // Navigates to the document named "players".
            .document("players").collection("player").document(playerId)
            // Retrieves the document with the specified playerId.
            .get().addOnCompleteListener { task ->
                // This lambda is executed when the Firestore operation is complete.
                if (task.isSuccessful) {
                    // If the task was successful, convert the document snapshot to a PlayersModel object.
                    val result = task.result.toObject(PlayersModel::class.java)
                    // If the conversion was successful and the result is not null, update the 'player' state.
                    if (result != null) {
                        player = result
                    }
                }

            }
    }
    // Text(text = "Player Name : ${player.fullName} Games Played : $qty")

    // Gets the current context, which might be needed for utility functions.
    val context = LocalContext.current

    // A Card composable displays information in a visually distinct and elevated container.
    Card(
        modifier = modifier
            .padding(8.dp) // Adds padding around the card.
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
                    .height(100.dp) // Sets a fixed height for the image.
                    .width(100.dp) // Sets a fixed width for the image.
            )
            // A Column composable arranges its children in a vertical sequence.
            Column(
                modifier = Modifier
                    .padding(8.dp) // Adds padding around the text within the Column.
                    .weight(1f) // Makes the Column take up the remaining available width.
            ) {
                // Displays the full name of the player with specific styling.
                Text(
                    text = player.fullName,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                // Displays the number of games played by the player.
                Text(
                    text = "Games Played : $qty",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                // Displays the category of the player.
                Text(
                    text = "Category : ${player.category}",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            // An IconButton is a clickable icon that provides visual feedback when pressed.
            IconButton(onClick = {
                // Calls a utility function to remove the player from the team.
                // 'removeAll = true' suggests it might remove all instances of this player.
                AppUtil.removePlayerFromTeam(playerId, context, removeAll = true)
            }) {
                // Displays the Delete icon as the visual representation of the button.
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Player" // Provides a content description for accessibility.
                )
            }
        }
    }
}
