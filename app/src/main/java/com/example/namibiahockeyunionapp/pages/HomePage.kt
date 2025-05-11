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
import androidx.compose.ui.text.font.FontFamily
import com.example.namibiahockeyunionapp.R

@Composable
fun HomePage(modifier: Modifier = Modifier) {

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



            Button(onClick = {
                Firebase.auth.signOut()
                navController.navigate("auth") {
                    popUpTo("home") { inclusive = true }
                }
            })
            {
                Text(text = "Logout")
            }
        }
    }
}
