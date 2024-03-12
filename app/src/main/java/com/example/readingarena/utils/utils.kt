package com.example.readingarena.utils

import android.content.Context
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import com.google.firebase.Timestamp



fun toastmessagel(context:Context, message:String){
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()

}

fun toastmessages(context:Context, message:String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

}

fun timetodate(ts: Timestamp):String{
    val date=SimpleDateFormat("d MMM").format(ts.toDate())
    return date

}