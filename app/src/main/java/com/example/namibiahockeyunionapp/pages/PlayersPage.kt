package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeyunionapp.components.PlayerCategoryView
import com.example.namibiahockeyunionapp.R


@Composable
fun PlayersPage(modifier: Modifier = Modifier, playersId: String) {
    Image(
        painter = painterResource(id = R.drawable.bg4),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

            ,horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
    ){
        Text(text= "Player Categories", style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,

        )
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlayerCategoryView(modifier)


    }


}
