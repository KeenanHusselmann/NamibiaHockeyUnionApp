package com.example.namibiahockeyunionapp

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

object AppUtil {

    // Quick helper function to show a toast message anywhere in the app
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // Adds a player to the current user's team
    fun addPlayerToTeam(playerId: String, context: Context) {
        val userDoc = Firebase.firestore.collection("users") // Reference to "users" collection
            .document(FirebaseAuth.getInstance().currentUser?.uid!!) // Get current user's document

        userDoc.get().addOnCompleteListener { // Fetch current data from Firestore
            if (it.isSuccessful) {
                // Get the current teamPlayers map from Firestore, or empty if null
                val currentTeam = it.result.get("teamPlayers") as? Map<String, Long> ?: emptyMap()

                // Check how many times the player has already been added (default 0)
                val currentPlayers = currentTeam[playerId] ?: 0

                // Increment the player's count by 1
                val newPlayers = currentPlayers + 1

                // Create the update map with the incremented value
                val updatedTeam = mapOf("teamPlayers.$playerId" to newPlayers)

                // Update Firestore with the new teamPlayers value
                userDoc.update(updatedTeam).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context, "Player Added Successfully")
                    } else {
                        showToast(context, "Player Not Added")
                    }
                }
            }
        }
    }

    // Removes a player from the user's team (can remove all or just one instance)
    fun removePlayerFromTeam(playerId: String, context: Context, removeAll: Boolean = false) {
        val userDoc = Firebase.firestore.collection("users") // Reference to "users" collection
            .document(FirebaseAuth.getInstance().currentUser?.uid!!) // Get current user's document

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                // Get the current teamPlayers map from Firestore, or empty if null
                val currentTeam = it.result.get("teamPlayers") as? Map<String, Long> ?: emptyMap()

                // Check how many times the player was added (default 0)
                val currentPlayers = currentTeam[playerId] ?: 0

                // Decrease the player's count by 1
                val newPlayers = currentPlayers - 1

                // Decide whether to delete the field or just update the count
                val updatedTeam = if (newPlayers <= 0 || removeAll)
                    mapOf("teamPlayers.$playerId" to FieldValue.delete()) // Remove player completely
                else
                    mapOf("teamPlayers.$playerId" to newPlayers) // Just update the count

                // Apply the update to Firestore
                userDoc.update(updatedTeam).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context, "Player Removed")
                    } else {
                        showToast(context, "Error! Please try again")
                    }
                }
            }
        }
    }
}
