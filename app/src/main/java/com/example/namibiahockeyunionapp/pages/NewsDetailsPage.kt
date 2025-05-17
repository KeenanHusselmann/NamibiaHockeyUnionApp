package com.example.namibiahockeyunionapp.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.namibiahockeyunionapp.model.HeadlinesModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun NewsDetailsPage(modifier: Modifier = Modifier, headlineId: String) {

        var headline by remember {
            mutableStateOf(HeadlinesModel())

        }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("news")
            .collection("headlines")
            .document(headlineId).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var result = it.result.toObject(HeadlinesModel::class.java)
                    if (result != null) {
                        headline = result


                    }
                }
            }
    }
        Column(
            modifier =modifier.fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ){
            Text(
                text = headline.headline,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = headline.date,
                fontSize = 12.sp,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Column{
                val pagerState = rememberPagerState(0) {
                    headline.images.size
                }
                HorizontalPager(state = pagerState,
                    pageSpacing = 24.dp
                ) {
                    AsyncImage(model = headline.images.get(it),
                        contentDescription = "Headline images",
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                DotsIndicator(
                    dotCount = headline.images.size,
                    type = ShiftIndicatorType(
                        DotGraphic(
                        color = MaterialTheme.colorScheme.primary,
                        size = 6.dp
                    )
                    ),
                    pagerState = pagerState
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = headline.category,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to Favourite"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {},
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                ){
                Text(text= "Share",
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Latest News",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = headline.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = headline.details,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
            )

            Spacer(modifier = Modifier.height(38.dp))

            if(headline.moreDetails.isNotEmpty())
                Text(
                text = "Publishing Details",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,

            )

            Spacer(modifier = Modifier.height(16.dp))

            headline.moreDetails.forEach {(key, value)->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                ){
                    Text(text ="$key : ",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp)

                    Text(text = value, fontSize =16.sp)
                }
            }



    }
}