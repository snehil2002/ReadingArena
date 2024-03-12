package com.example.readingarena.bookviewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingarena.bookdata.Item
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.repo.Bookrepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Bookidviewmodel @Inject constructor(private val repo:Bookrepo):ViewModel() {
    suspend fun getbookbyid(bookid:String):DataorException<Item>?{
        return repo.getbookbyid(bookid)

    }

}