package com.example.readingarena.userdata

data class Muser(val id:String?,
val uid:String,
val username:String,
val quote:String,
val profession:String,
val avatarurl:String){
    constructor() :  this("","","","","","")
    fun map():MutableMap<String,String?>{
        return mutableMapOf("id" to this.id,
            "uid" to this.uid,
            "username" to this.username,
            "quote" to this.quote,
            "profession" to this.profession,
            "avatarurl" to this.avatarurl
        )
    }
}
