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

@Composable
fun TeamsView(modifier: Modifier = Modifier, playerId : String, qty : Long) {

    var player by remember {
        mutableStateOf(PlayersModel())

    }
    LaunchedEffect (key1 = Unit ) {
        Firebase.firestore.collection("data")
            .document("players").collection("player").document(playerId)
            .get().addOnCompleteListener(){
                if(it.isSuccessful){
                    val result = it.result.toObject(PlayersModel::class.java)
                    if(result != null){
                        player = result
                    }
                }
            }

    }
  //  Text(text = "Player Name : ${player.fullName} Games Played : $qty")


    var context = LocalContext.current

    Card(
        modifier = modifier
            .padding(8.dp)
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
                    .height(100.dp)
                    .width(100.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
                    .weight(1f)
            ){
                Text(text = player.fullName,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
                Text( text = "Games Played : $qty",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                    )
                Text( text = "Category : ${player.category}",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            IconButton(onClick = {
                AppUtil.removePlayerFromTeam(playerId, context, removeAll = true)

            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Player"
                )
            }

        }

    }


}