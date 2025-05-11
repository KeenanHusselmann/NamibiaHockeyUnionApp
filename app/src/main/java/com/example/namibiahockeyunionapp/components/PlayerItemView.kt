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


@Composable
fun PlayerItemView(modifier: Modifier = Modifier, player: PlayersModel) {



    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable{
                GlobalNavigation.navController.navigate("player-details/"+player.id)
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp),

    ){
        Row (
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ){
            AsyncImage(
                    model = player.imageUrl,
            contentDescription = "Image of ${player.fullName}",
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
            )
            Column{
                Text(text = player.fullName,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )

            }

        }

    }


}