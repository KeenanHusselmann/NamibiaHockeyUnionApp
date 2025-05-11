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

@Composable
fun LoadingPage(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "Logo Rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000)
        ), label = "Logo Rotation Animation"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RotatingLogo(rotation = rotation)
    }
}

@Composable
fun RotatingLogo(rotation: Float) {
    Image(
        painter = painterResource(id = R.drawable.logotr),
        contentDescription = "App Logo",
        modifier = Modifier
            .size(150.dp)
            .rotate(rotation)
    )
}

/*
@Preview(showBackground = true)
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation()
}

 */
