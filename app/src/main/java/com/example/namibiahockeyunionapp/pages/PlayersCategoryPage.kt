package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.namibiahockeyunionapp.R
import com.example.namibiahockeyunionapp.components.PlayerItemView
import com.example.namibiahockeyunionapp.model.PlayerCategoryModel
import com.example.namibiahockeyunionapp.model.PlayersModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
@Composable
fun PlayersCategoryPage(modifier: Modifier = Modifier, playerCategoryId: String) {
    val playersList = remember {
        mutableStateOf<List<PlayersModel>>(emptyList())

    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("players")
            .collection("player")
            .whereEqualTo("category", playerCategoryId)
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(PlayersModel::class.java)
                    }
                    playersList.value = resultList.plus(resultList).plus(resultList).plus(resultList).plus(resultList).plus(resultList).plus(resultList).plus(resultList).plus(resultList).plus(resultList)
                }
            }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg4),
            contentDescription = "Background Image", // It's good practice to provide a content description
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(playersList.value) { item ->
                PlayerItemView(modifier, item)
            }

        }
    }
}
