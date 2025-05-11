package com.example.namibiahockeyunionapp.model

data class TeamCategoryModel(
    val uid : String = "",
    val name : String = "",
    val imageUrl : String = "",
    val teamPlayers : Map<String,Long> = mapOf()
)

