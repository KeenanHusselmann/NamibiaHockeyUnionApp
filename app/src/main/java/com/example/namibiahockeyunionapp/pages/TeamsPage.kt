package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.components.TeamsView
import com.example.namibiahockeyunionapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


@Composable
fun TeamsPage(modifier: Modifier = Modifier, categoryId: String, playerId: String, qty: Long, navController: NavHostController) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    DisposableEffect(key1 = Unit) {
      var listener =   Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener{it, _ ->

                if(it != null){
                    val result = it.toObject(UserModel::class.java)
                    if (result!= null) {
                        userModel.value = result
                    }
                }
            }
        onDispose {
            listener.remove()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg4),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
        )
        {
            Text(
                text = "Men's Inline Team A", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            LazyColumn {
                items(userModel.value.teamPlayers.toList(), key = { it.first }) { (playerId, qty) ->
                    TeamsView(modifier = modifier, playerId = playerId, qty = qty)
                }
            }
        }
    }
}



