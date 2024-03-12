package com.example.readingarena.dataorexception

import androidx.compose.runtime.State
import com.example.readingarena.bookdata.Item

data class DataorException<T>(
    var data:T?=null,
    var loading:Boolean?=null,
    var exception: java.lang.Exception?=null

)
