package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.AppUtil
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.example.namibiahockeyunionapp.model.PlayersModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun PlayerDetailsPage(modifier: Modifier = Modifier, playerId: String) {

    var player by remember {
        mutableStateOf<PlayersModel?>(null)

    }

    var context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("players")
            .collection("player")
            .document(playerId).get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                      var result = it.result.toObject(PlayersModel::class.java)
                        if (result != null) {
                            player = result
                        }
                    }
                }

    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ){
        Text(text = player?.fullName ?: "",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
            )

        Spacer(modifier = Modifier.height(16.dp))

        Column(

        ) {

                AsyncImage(model = player?.imageUrl,
                    contentDescription = "Banner images",
                    modifier = Modifier
                        .height(180.dp)
                        .width(180.dp).fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))

                )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                AppUtil.addPlayerToTeam(context = context, playerId = playerId)


            },
                modifier = Modifier.fillMaxWidth().height(34.dp)){
                Text(text = "Add to Team", fontSize = 16.sp)

            }
            Spacer(modifier = Modifier.height(20.dp))

            if(player?.otherDetails?.isNotEmpty() == true)Text(text = "Player Details",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            player?.otherDetails?.forEach { (key, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ){
                    Text(text = "$key : ", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(text = value, fontSize = 16.sp)
                }


            }




        }
    }
}
