package com.example.namibiahockeyunionapp.pages // Defines the package name for this file.

// Import necessary Jetpack Compose UI components and utilities
import androidx.compose.foundation.layout.Arrangement // Defines arrangements for children in layouts like Column and Row.
import androidx.compose.foundation.layout.Column // Used for arranging UI elements vertically.
import androidx.compose.foundation.layout.Spacer // Used to add empty space between UI elements.
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a composable fill the maximum available size.
import androidx.compose.foundation.layout.height // Modifier to set the height of a composable.
import androidx.compose.foundation.layout.padding // Modifier to add padding around a composable.
import androidx.compose.material3.Text // A Material Design text composable for displaying text.
import androidx.compose.runtime.Composable // Marks a function as a Composable function.
import androidx.compose.ui.Alignment // Defines alignment options for layout children.
import androidx.compose.ui.Modifier // A set of instructions to draw or layout a UI element.
import androidx.compose.ui.text.TextStyle // Defines styling for text.
import androidx.compose.ui.text.font.FontFamily // Defines the font family for text.
import androidx.compose.ui.text.font.FontWeight // Defines the thickness of the font glyphs.
import androidx.compose.ui.unit.dp // Dimension unit representing density-independent pixels.
import androidx.compose.ui.unit.sp // Dimension unit representing scaled pixels for text size.

// Import custom composables
import com.example.namibiahockeyunionapp.components.PlayerCategoryView // Imports the custom Composable function that displays the player categories.

/**
 * Composable function representing the page that displays player categories.
 * It shows a title and then delegates the rendering of the categories to a custom view.
 *
 * @param modifier Optional modifier to apply to the root layout of the page.
 * @param playersId A string identifier, though it is currently unused in this composable.
 */
@Composable
fun PlayersPage(modifier: Modifier = Modifier, playersId: String) {
    // Root Column layout to arrange the title and categories vertically.
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the column fill the entire screen size.
            .padding(20.dp), // Add padding around the content within the column.
        horizontalAlignment = Alignment.CenterHorizontally, // Center the children (Text and PlayerCategoryView) horizontally within the column.
        verticalArrangement = Arrangement.Top // Arrange children starting from the top of the column.
    ){
        // Display the title "Player Categories".
        Text(text= "Player Categories",
            // Apply text styling.
            style = TextStyle(
                fontSize = 28.sp, // Set font size.
                fontWeight = FontWeight.SemiBold, // Set font weight.
                fontFamily = FontFamily.SansSerif, // Set font family.
            )
        )
        // Add vertical space between the title and the category view.
        Spacer(modifier = Modifier.height(16.dp))

        // Call the custom Composable that's responsible for displaying the actual list or grid of player categories.
        // The external modifier is passed down to this custom view.
        PlayerCategoryView(modifier)
    }

    // Note: The 'playersId' parameter is accepted by this Composable but is not
    // currently used within the function's logic. It might be intended for future use,
    // like fetching specific categories based on this ID, but in the current code, it's unused.
}
