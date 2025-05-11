package com.example.namibiahockeyunionapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.GlobalNavigation
import com.example.namibiahockeyunionapp.model.NewsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun News(modifier: Modifier = Modifier){
    val newsList = remember {
        mutableStateOf<List<NewsModel>>(emptyList())
    }

    LaunchedEffect(key1 = Unit){
        Firebase.firestore.collection("data")
            .document("news")
            .collection("categories")
            .get().addOnCompleteListener() {
                if(it.isSuccessful){
                    val resultList = it.result.documents.mapNotNull { doc->
                        doc.toObject(NewsModel::class.java)
                    }
                    newsList.value = resultList
                }
            }
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(newsList.value) { item ->
            NewsItem(category = item)
        }
    }
}
@Composable
fun NewsItem(category : NewsModel) {
    Card(
        modifier = Modifier.size(150.dp)
            .clickable{
                GlobalNavigation.navController.navigate("news-headlines/"+category.id)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(text = category.name, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))

            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier.size(150.dp)
            )



        }
    }



}