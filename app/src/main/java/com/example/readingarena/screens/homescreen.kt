package com.example.readingarena.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.bookviewmodels.Udetailsviewmodel
import com.example.readingarena.bookviewmodels.Userbooksviewmodel
import com.example.readingarena.screencomponents.floatingbutton
import com.example.readingarena.userdata.Muser
import com.example.readingarena.utils.toastmessagel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun homescreen(navcontroller:NavController,ubooksviewmodel:Userbooksviewmodel,
udetailsviewmodel: Udetailsviewmodel){

    val auth=FirebaseAuth.getInstance()
    var uname= ""
    if(udetailsviewmodel.isloading==false && udetailsviewmodel.userdetail.isNotEmpty()){
        uname=udetailsviewmodel.userdetail[0].username
    }
    Log.d("Debugu", "Username: $uname")

    val context= LocalContext.current
    val alluserbooks = ubooksviewmodel.userbooks
    var ubooks= emptyList<Mbook>()
    if (alluserbooks.value.loading==false){
        ubooks=alluserbooks.value.data!!.filter {
            it.userId==auth.currentUser?.uid.toString()
        }
    val readingrightnow=ubooks.filter { it.startedReading!=null && it.finishedReading==null }
    val readinglist= ubooks.filter { it.startedReading==null && it.finishedReading==null }



    Scaffold(topBar = { topappbar(title = "Reading Arena", ishome = true
    , onfavourite = {navcontroller.navigate(Readerscreens.Likedscreen.name)}, onlogout = {auth.signOut()
            toastmessagel(context,"Logged Out")
    navcontroller.navigate(Readerscreens.Loginscreen.name){
        popUpTo(Readerscreens.Splashscreen.name){
            inclusive=false
        }
    }
    })},floatingActionButton = {floatingbutton(onclick = {navcontroller.navigate(Readerscreens.Searchscreen.name)})}) {
        if (alluserbooks.value.loading==true){
            LinearProgressIndicator()
        }
        else{
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(
                    top = 10.dp,
                    bottom = 5.dp, start = 10.dp, end = 10.dp
                )
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
                Text(text = "Your Reading\n Activity Right Now..."
                , color = Color.LightGray, fontSize = 20.sp)
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription ="" ,
                modifier = Modifier
                    .size(45.dp)
                    .clickable { navcontroller.navigate(Readerscreens.Statscreen.name)}, tint = Color.Cyan)
                Text(text = uname,
                color = Color.LightGray, fontWeight = FontWeight.Bold)}

            }
            if(readingrightnow.isNullOrEmpty()){
                Column(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Your Reading Activity is Empty!\nYou are not Reading any Book right now", fontSize = 18.sp,
                    color = Color(0xFF009688), fontWeight = FontWeight.SemiBold)

                }
            }
            else{
            LazyRow(){
                items(readingrightnow){
                    bookcard(mbook = it, buttonlabel = "READING"){
                        navcontroller.navigate("${Readerscreens.Updatescreen.name}/${it.id}")
                    }
                }
            }}
            Text(text = "Reading List"
                , color = Color.LightGray, fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp,
                bottom = 5.dp, start = 10.dp))
            if(readinglist.isNullOrEmpty()){
                Column(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Your Reading list is Empty",fontSize = 18.sp,
                        color = Color(0xFF009688), fontWeight = FontWeight.SemiBold)

                }
            }
            else{
            LazyRow(){
                items(readinglist){
                    bookcard(mbook = it, buttonlabel = "NOT STARTED"){
                        navcontroller.navigate("${Readerscreens.Updatescreen.name}/${it.id}")
                    }
                }
            }}



        }}
    }
}}