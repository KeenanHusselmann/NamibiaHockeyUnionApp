package com.example.namibiahockeyunionapp.model

// Data class representing a headline item in the app
data class HeadlinesModel(
    val id : String = "",
    val headline : String = "",
    val description : String = "",
    val category : String = "",
    val date : String = "",
    val details : String = "",
    val images : List<String> =  emptyList(),
    val moreDetails : Map<String,String> = emptyMap()
)
