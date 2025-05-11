package com.example.namibiahockeyunionapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.namibiahockeyunionapp.model.TeamModel
import com.example.namibiahockeyunionapp.model.UserModel

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.localizedMessage)//message ?: "Error"

                }
            }
    }

    fun signup(
        email: String,
        name: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var userId = it.result?.user?.uid

                    val userModel = UserModel(name, email, userId!!)
                    firestore.collection("users").document(userId).set(userModel)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                onResult(true, null)
                            } else {
                                onResult(false, "Something went wrong")
                            }


                        }

                } else {
                    onResult(false, it.exception?.localizedMessage)//message ?: "Error"
                }
            }
    }


    fun teamSignup(
        teamName: String,
        category: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        // Generate a unique ID for the team
        val teamId = firestore.collection("teams").document().id

        val teamsModel = TeamModel(teamName, category, teamId)

        firestore.collection("teams").document(teamId).set(teamsModel)
            .addOnCompleteListener { dbTask ->
                if (dbTask.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, "Something went wrong: ${dbTask.exception?.localizedMessage}")
                }
            }
    }

}

