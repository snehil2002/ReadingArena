package com.example.readingarena.bookviewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingarena.bookdata.Item
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.dataorexception.Resource
import com.example.readingarena.repo.Bookrepo
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Bookviewmodel @Inject constructor(private val repo:Bookrepo) :ViewModel(){
    var allbooks:List<Item>? by mutableStateOf(listOf())
    var isloading by mutableStateOf(true)
    init{
        getallbook("flutter")

    }
    fun getallbook(query: String){
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty())
                return@launch
            try{
                when(val response=repo.getallbooks(query)){
                    is Resource.Success->{
                        allbooks= response.data!!
                        Log.d("zzzz","zzzz")
                        if(!allbooks.isNullOrEmpty())
                            isloading=false}
                    is Resource.Error->{
                            isloading=false
                        Log.d("xxxx","xxxx")
                        Log.e("Network",response.message.toString())
                        }
                     else->{
                         isloading=false
                         Log.d("cccc","cccc")
                        }
                    }


                }catch(e:Exception){
                    Log.d("network",e.message.toString())
                    Log.d("vvvv","vvvv")
            }

        }
    }
}