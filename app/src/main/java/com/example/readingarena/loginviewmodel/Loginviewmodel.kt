package com.example.readingarena.loginviewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class Loginviewmodel():ViewModel() {

    var loading= mutableStateOf(false)
    val auth=FirebaseAuth.getInstance()

    fun login(email:String,pass:String,success:()->Unit,failure:()->Unit){
        viewModelScope.launch { auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {
            success()
        }.addOnFailureListener{
            failure()


        }}
    }

    fun sinin(email:String,pass:String,success:()->Unit,failure: () -> Unit){
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener {
                success()
            }.addOnFailureListener{
                failure()
            }
        }

    }
}