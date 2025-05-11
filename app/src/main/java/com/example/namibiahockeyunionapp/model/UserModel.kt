package com.example.namibiahockeyunionapp.model

data class UserModel(
    val email: String = "",
    val name: String = "",
    val uid : String = "",
    val teamPlayers : Map<String, Long> = mapOf()
)
