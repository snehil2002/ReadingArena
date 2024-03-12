package com.example.readingarena.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.bookviewmodels.Udetailsviewmodel
import com.example.readingarena.bookviewmodels.Userbooksviewmodel
import com.example.readingarena.utils.timetodate
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable fun statscreen(navcontroller:NavController, ubooksviewmodel:Userbooksviewmodel,udetailsviewmodel: Udetailsviewmodel){
    var userdetails=udetailsviewmodel.userdetail
    var uname=""
    if(!udetailsviewmodel.isloading && userdetails.isNotEmpty()){
        uname=userdetails[0].username
    }
    var userbooks=ubooksviewmodel.userbooks.value.data!!.filter {
        it.userId==userdetails[0].uid
    }
    var reading=userbooks.filter {
        it.startedReading!=null && it.finishedReading==null
    }
    var finishedreading=userbooks.filter {
        it.startedReading!=null && it.finishedReading!=null
    }

    Scaffold(topBar = { topappbar(title = "Book Stats", ishome = false, onback = {navcontroller.popBackStack()})}) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Hi $uname", color = Color.LightGray, fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp, modifier = Modifier.padding(vertical = 20.dp, horizontal = 60.dp)
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(10.dp), shape = RoundedCornerShape(50), color = Color.DarkGray,
                elevation = 10.dp
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 40.dp)) {
                    Text(
                        text = "Your Stats", color = Color.LightGray, fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Divider(modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    color = Color.LightGray)
                    Text(
                        text = "You're reading: ${reading.size} books", color = Color.LightGray,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "You've read: ${finishedreading.size} books", color = Color.LightGray,
                        fontSize = 18.sp
                    )

                }

            }
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .height(1.dp),
                color = Color.Black)
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)){
                items(finishedreading){
                    statrow(mbook = it)
                    
                }
            }


        }

    }
}

@Composable fun statrow(mbook:Mbook){
    var isliked by remember {
        mutableStateOf(mbook.liked!!)
    }
    LaunchedEffect(isliked){
        FirebaseFirestore.getInstance().collection("books").document(mbook.id!!).update("liked",isliked)
    }
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(horizontal = 10.dp, vertical = 5.dp), color = Color.DarkGray, elevation = 3.dp) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)) {
            Image(painter = rememberImagePainter(data = mbook.image.toString()), contentDescription ="" ,
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp))
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)) {
                Row(modifier =Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = mbook.title.toString(), color = Color.LightGray, fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(220.dp)
                    )
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "",
                    tint = if (isliked) Color(0xFFE06D6D) else Color.LightGray, modifier = Modifier.size(26.dp).clickable { isliked=!isliked })

                }
                Text(
                    text = mbook.authors.toString(), color = Color.LightGray,
                    fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Started: ${timetodate(mbook.startedReading!!)}", color = Color.LightGray,
                    fontSize = 16.sp, fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Finished: ${timetodate(mbook.finishedReading!!)}", color = Color.LightGray,
                    fontSize = 16.sp, fontStyle = FontStyle.Italic
                )


            }

        }


    }

}