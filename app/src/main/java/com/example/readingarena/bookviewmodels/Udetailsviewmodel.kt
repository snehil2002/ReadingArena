package com.example.readingarena.bookviewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingarena.dataorexception.Resource
import com.example.readingarena.repo.Firerepo
import com.example.readingarena.userdata.Muser
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Udetailsviewmodel @Inject constructor(private val firerepo: Firerepo):ViewModel() {
var userdetail :List<Muser> by mutableStateOf(listOf())
    var isloading by mutableStateOf(true)

    init {
        getuserdetails(FirebaseAuth.getInstance().currentUser?.uid.toString())
    }
    fun getuserdetails(uid:String){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val response=firerepo.getuserdetails(uid)
                when(response){
                    is Resource.Success->{
                        userdetail= response.data!!
                        Log.d("dddd",userdetail[0].username)
                        if(userdetail.isNotEmpty())
                            isloading=false
                    }
                    is Resource.Error->{
                        isloading=false
                        Log.d("gggg",response.message.toString())
                    }
                    else ->{
                        isloading=false
                        Log.d("ffff","eeee")

                    }
                }
            }catch (e:Exception){
                Log.d("aaaa",e.message.toString())
            }
        }
    }
}