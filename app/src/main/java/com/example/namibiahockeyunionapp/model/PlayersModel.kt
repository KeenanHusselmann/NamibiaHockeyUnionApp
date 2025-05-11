package com.example.namibiahockeyunionapp.model

data class PlayersModel(

    val id: String = "",
    val category: String = "",
    val fullName: String = "",
    val imageUrl: String = "",
    val otherDetails : Map<String, String> = emptyMap()
)
