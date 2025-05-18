package com.example.namibiahockeyunionapp.model

// Represents a user in the app
// Example coach, manager, or admin
data class UserModel(
    val email: String = "",
    val name: String = "",
    val uid : String = "",
    val teamPlayers : Map<String, Long> = mapOf()
)
