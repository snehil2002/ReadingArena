package com.example.readingarena.bookdata

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

data class Mbook(@Exclude var id:String?=null,
                 @get:PropertyName("user_id")
                 @set:PropertyName("user_id")
                 var userId:String?=null,
                 @get:PropertyName("google_book_id")
                 @set:PropertyName("google_book_id")
                 var googleBookId:String?=null,
                 var title:String?=null,
                 var authors:String?=null,
                 var image:String?=null,
                 var description:String?=null,
                 var notes:String?=null,
                 @get:PropertyName("published_date")
                 @set:PropertyName("published_date")
                 var publishedDate:String?=null,
                 var rating:Double?=null,
                 @get:PropertyName("page_count")
                 @set:PropertyName("page_count")
                 var pageCount:String?=null,
                 @get:PropertyName("started_reading")
                 @set:PropertyName("started_reading")
                 var startedReading:Timestamp?=null,
                 @get:PropertyName("finished_reading")
                 @set:PropertyName("finished_reading")
                 var finishedReading:Timestamp?=null,
                 var liked:Boolean?=null
)
