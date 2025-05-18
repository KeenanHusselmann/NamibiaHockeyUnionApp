package com.example.namibiahockeyunionapp.model

// Represents a team category, which includes a group of players and related info
// Like Real Madrid or Man U, not these bad Namibian football teams.
data class TeamCategoryModel(
    val uid : String = "",
    val name : String = "",
    val imageUrl : String = "",
    val teamPlayers : Map<String,Long> = mapOf()
)

