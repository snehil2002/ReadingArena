package com.example.readingarena.repo

import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.dataorexception.Resource
import com.example.readingarena.userdata.Muser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Firerepo @Inject constructor(private val firestore:FirebaseFirestore) {
    val bookquery=firestore.collection("books")
    val userquery=firestore.collection("users")

    fun getuserbooks()= callbackFlow<DataorException<List<Mbook>>> {
        val listener=bookquery.addSnapshotListener(EventListener{
            value, error ->
            if(error!=null){
                trySend(DataorException(listOf(),false,error))
                return@EventListener
            }
            if(value!=null){
                val books=value.documents.map {
                    it.toObject(Mbook::class.java)!!
                }
                trySend(DataorException(books,false,null))
            }
        })
        awaitClose{listener.remove()}
    }.onStart { emit(DataorException(listOf(),true,null)) }

    suspend fun getuserdetails(userid:String):Resource<List<Muser>>{
        return try {
            Resource.Loading(data = true)
            val userdetail=userquery.whereEqualTo("uid",userid).get().await().documents.mapNotNull {
                it.toObject(Muser::class.java)!!
            }
            if (!userdetail.isNullOrEmpty()){
                Resource.Loading(data = false)
            }
            Resource.Success(data = userdetail)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }

}
