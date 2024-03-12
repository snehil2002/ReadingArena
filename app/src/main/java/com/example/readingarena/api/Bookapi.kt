package com.example.readingarena.api

import com.example.readingarena.bookdata.Book
import com.example.readingarena.bookdata.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Bookapi {

    @GET("volumes")
    suspend fun getallbooks(@Query("q") query:String):Book

    @GET("volumes/{volumeId}")
    suspend fun getbookbyid(@Path("volumeId") volumeId:String):Item
}