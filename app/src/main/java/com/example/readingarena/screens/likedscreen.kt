package com.example.readingarena.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.bookviewmodels.Userbooksviewmodel
import com.example.readingarena.screencomponents.textbutton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable fun likedscreen(navcontroller:NavController, ubooksviewmodel:Userbooksviewmodel){
    var likeduserbooks=ubooksviewmodel.userbooks.value.data!!.filter {
        it.userId==FirebaseAuth.getInstance().currentUser!!.uid && it.liked!!
    }


    Scaffold(topBar = { topappbar(title = "Favourite Books", ishome = false, onback = {navcontroller.popBackStack()})}) {
        Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Books Liked By You >>",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp),
            color = Color.LightGray
        )
            if (likeduserbooks.isEmpty()){
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = "You have not liked any Book yet!",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp),
                    color = Color(0xFF009688)
                )}

            }
            else{
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(likeduserbooks) {
                gridcard(book = it)

            }

        }}
    }


    }
    

}

@Composable fun gridcard(book:Mbook){
    Column() {


        Surface(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(300.dp)
                .width(200.dp),
            elevation = 5.dp, color = Color.DarkGray
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(data = book.image), contentDescription = "",
                    modifier = Modifier
                        .height(220.dp)
                        .width(185.dp)
                        .padding(1.dp)
                )
                Text(
                    text = book.title.toString(), fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = Color.LightGray, modifier = Modifier
                        .padding(3.dp),
                    maxLines = 2, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.toString(),
                    fontSize = 15.sp,
                    color = Color.LightGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(3.dp)
                )


            }


        }
        Surface(
            modifier = Modifier
                .padding(
                    start = 10.dp, end = 10.dp, bottom = 15.dp, top = 1.dp
                )
                .height(40.dp)
                .width(200.dp),
            elevation = 5.dp, color = Color.DarkGray
        ) {

            Row(
                modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                textbutton(label = "Remove", onclick = {
                    FirebaseFirestore.getInstance().collection("books").document(book.id!!).update("liked",false)
                })
            }

        }
    }

    
}