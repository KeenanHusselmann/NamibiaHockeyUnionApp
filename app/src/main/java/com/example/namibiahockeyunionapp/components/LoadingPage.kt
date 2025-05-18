package com.example.namibiahockeyunionapp.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.namibiahockeyunionapp.R

/**
 * A composable function that displays a loading page with a rotating logo animation.
 *
 * @param modifier Modifier to apply styling or layout behavior to this composable.
 */
@Composable
fun LoadingPage(modifier: Modifier = Modifier) {
    // Creates an InfiniteTransition that can run animations indefinitely.
    val infiniteTransition = rememberInfiniteTransition(label = "Logo Rotation")
    // Animates a float value from 0f to 360f infinitely, creating a rotation effect.
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000) // Defines the animation duration of 2000 milliseconds.
        ),
        label = "Logo Rotation Animation" // Label for the animation (useful for debugging).
    )

    // A Box composable is used to position its children relative to its edges.
    Box(
        modifier = modifier.fillMaxSize(), // Makes the Box fill the entire available screen space.
        contentAlignment = Alignment.Center // Centers the content within the Box.
    ) {
        // Calls the RotatingLogo composable to display the rotating logo.
        RotatingLogo(rotation = rotation)
    }
}

/**
 * A composable function that displays the rotating logo image.
 *
 * @param rotation The current rotation angle of the logo in degrees.
 */
@Composable
fun RotatingLogo(rotation: Float) {
    // Displays an image using a painter resource.
    Image(
        painter = painterResource(id = R.drawable.logotr), // Loads the image from the specified resource ID.
        contentDescription = "App Logo", // Provides a content description for accessibility.
        modifier = Modifier
            .size(150.dp) // Sets the size of the logo to 150dp x 150dp.
            .rotate(rotation) // Applies the rotation animation to the image.
    )
}

/*
@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation()
}

 */
