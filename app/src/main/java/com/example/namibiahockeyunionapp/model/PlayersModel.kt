package com.example.namibiahockeyunionapp.model


// Represents an individual player and their some of their details (CR7 again)
data class PlayersModel(

    val id: String = "",
    val category: String = "",
    val fullName: String = "",
    val imageUrl: String = "",
    val otherDetails : Map<String, String> = emptyMap()
)
