package com.example.namibiahockeyunionapp

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

object AppUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }

    fun addPlayerToTeam(playerId: String, context: Context) {
        val userDoc = Firebase.firestore.collection("users")//query the current user document
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)//id of current user

        userDoc.get().addOnCompleteListener{//used to allow updating
            if(it.isSuccessful){
                val currentTeam = it.result.get("teamPlayers") as? Map<String, Long> ?: emptyMap()

                //val currentPlayers
                val currentPlayers = currentTeam[playerId] ?: 0 // "No Player Added"

                //val newPlayers
                val newPlayers = currentPlayers + 1 //"New Player Added"

                val updatedTeam =  mapOf("teamPlayers.$playerId" to newPlayers)

                userDoc.update(updatedTeam).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context, "Player Added Successfully")
                    }else{
                        showToast(context, "Player Not Added")
                    }
                }

            }
        }

    }
    fun removePlayerFromTeam(playerId: String, context: Context, removeAll : Boolean = false) {
        val userDoc = Firebase.firestore.collection("users")//query the current user document
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)//id of current user

        userDoc.get().addOnCompleteListener{//used to allow updating
            if(it.isSuccessful){
                val currentTeam = it.result.get("teamPlayers") as? Map<String, Long> ?: emptyMap()

                //val currentPlayers
                val currentPlayers = currentTeam[playerId] ?: 0 // "No Player Added"

                //val newPlayers
                val newPlayers = currentPlayers - 1 //"New Player Added"

                val updatedTeam =
                    if(newPlayers <= 0 || removeAll)
                        mapOf("teamPlayers.$playerId" to FieldValue.delete())
                    else
                    mapOf("teamPlayers.$playerId" to newPlayers)

                userDoc.update(updatedTeam).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context, "Player Removed")
                    }else{
                        showToast(context, "Error! Please try again")
                    }
                }

            }
        }

    }
}