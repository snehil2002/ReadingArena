package com.example.readingarena.repo

import com.example.readingarena.api.Bookapi
import com.example.readingarena.bookdata.Item
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.dataorexception.Resource
import javax.inject.Inject

class Bookrepo@Inject constructor(private val api:Bookapi) {

    suspend fun getallbooks(query:String):Resource<List<Item>>{
        return try {
            Resource.Loading(data = true)
            val allbooks=api.getallbooks(query).items
            if(allbooks.isNotEmpty())
                Resource.Loading(data=false)
            Resource.Success(data = allbooks)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }

    var bookbyid:DataorException<Item>? =DataorException<Item>()
    suspend fun getbookbyid(volumeid:String):DataorException<Item>?{
        try{
            bookbyid?.loading=true
        bookbyid?.data=api.getbookbyid(volumeid)
        if(bookbyid?.data!=null){
            bookbyid?.loading=false
        }}
        catch (e:java.lang.Exception){

        }
        return bookbyid
    }
}