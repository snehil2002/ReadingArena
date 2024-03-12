package com.example.readingarena.bookviewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.repo.Firerepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Userbooksviewmodel @Inject constructor(private val firerepo: Firerepo):ViewModel(){
    var userbooks:MutableState<DataorException<List<Mbook>>> = mutableStateOf(DataorException(listOf(),true,null))
    init{
        getuserbooks()
    }

    private fun getuserbooks(){
        viewModelScope.launch (){
            firerepo.getuserbooks().collect{
                userbooks.value=it
            }
            if (userbooks.value.data!!.isNotEmpty())
                userbooks.value.loading=false
        }
    }

}