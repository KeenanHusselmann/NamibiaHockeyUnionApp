package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.namibiahockeyunionapp.components.HeadlineItemView
import com.example.namibiahockeyunionapp.model.HeadlinesModel
import com.example.namibiahockeyunionapp.model.NewsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun NewsHeadlinesPage(modifier: Modifier = Modifier, categoryId: String) {
    val headlinesList = remember {
        mutableStateOf<List<HeadlinesModel>>(emptyList())
    }

    LaunchedEffect(key1 = Unit){
        Firebase.firestore.collection("data")
            .document("news")
            .collection("headlines")
            .whereEqualTo("category", categoryId)
            .get().addOnCompleteListener() {
                if(it.isSuccessful){
                    val resultList = it.result.documents.mapNotNull { doc->
                        doc.toObject(HeadlinesModel::class.java)
                    }
                    headlinesList.value = resultList
                }
            }
    }


    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ){
        items(headlinesList.value) { item ->
            HeadlineItemView(Modifier,item)
        }
    }
}