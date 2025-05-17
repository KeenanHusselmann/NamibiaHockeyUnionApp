package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.example.namibiahockeyunionapp.components.Banner
import com.example.namibiahockeyunionapp.components.Header
import com.example.namibiahockeyunionapp.components.News
import androidx.compose.foundation.layout.Box
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.example.namibiahockeyunionapp.R
import androidx.compose.ui.platform.LocalUriHandler
@Composable
fun HomePage(modifier: Modifier = Modifier) {

    val uriHandler = LocalUriHandler.current
    val websiteUrl = "https://namibiahockey.org/"

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg4), contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {


            Header()

            Banner(modifier = Modifier.height(150.dp))

            Spacer(modifier = Modifier.height(40.dp))


            Text(
                "Sports News", style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            News(modifier)


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(60.dp)

                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF00C853), Color(0xFF00E5FF)) // Green to Cyan
                            ),
                            shape = RoundedCornerShape(30.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            uriHandler.openUri(websiteUrl)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),

                    ) {
                        Text(
                            text = "Visit Website",
                            fontSize = 22.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

        }
    }
}