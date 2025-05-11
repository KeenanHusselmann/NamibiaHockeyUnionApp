package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.GlobalNavigation.navController
import com.example.namibiahockeyunionapp.model.PlayerCategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun PlayerCategoryView(modifier: Modifier = Modifier) {

    val playerCategoryList = remember {
        mutableStateOf<List<PlayerCategoryModel>>(emptyList())

    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("players")
            .collection("playerCategories")
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(PlayerCategoryModel::class.java)
                    }
                    playerCategoryList.value = resultList
                }
            }
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(playerCategoryList.value) { item ->
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
               PlayerCategoryItem(playerCategory = item)
            }
        }
    }
}

@Composable
fun PlayerCategoryItem(playerCategory: PlayerCategoryModel) {


    Card(

        modifier = Modifier
            .size(300.dp)
            .padding(16.dp)
            .clickable{
               GlobalNavigation.navController.navigate("players-category/"+playerCategory.id)

            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        )
        {
            Text(
                text = playerCategory.name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
          AsyncImage(
              model = playerCategory.imageUrl,
              contentDescription = "Image of ${playerCategory.name}",
              modifier = Modifier.size(200.dp)
          )

          Spacer(modifier = Modifier.height(4.dp))



      }
  }

}