package com.example.readingarena.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readingarena.R
import com.example.readingarena.bookdata.Item
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.screencomponents.stylebutton
import com.google.firebase.firestore.FirebaseFirestore


@Composable fun topappbar(title:String,ishome:Boolean
,onfavourite:()->Unit={},onback:()->Unit={},onlogout:()->Unit={}){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        .padding(horizontal = 10.dp), color = Color.DarkGray) {
        Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.SpaceBetween) {
            if (ishome)
                Icon(imageVector = Icons.Default.Favorite, contentDescription ="" 
                , modifier = Modifier
                        .size(25.dp)
                        .clickable { onfavourite()}, tint = Color.Cyan)
            else
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" 
                ,modifier = Modifier
                        .size(25.dp)
                        .clickable { onback() }, tint = Color.White)

            Text(text = title, fontSize = 28.sp,
                color = Color.LightGray, fontFamily = FontFamily(Font(R.font.pacificofont)),
                )
            if (ishome)
                Icon(painter = painterResource(id = R.drawable.logout), contentDescription = ""
                ,modifier = Modifier
                        .size(25.dp)
                        .clickable { onlogout() }, tint = Color.White)
            else
                Box(modifier = Modifier.size(25.dp))
            
            
        }
        
    }
}

@Composable fun bookcard(mbook: Mbook,buttonlabel:String,oncardclick:()->Unit){
    var isliked by remember{ mutableStateOf(mbook.liked!!) }
    LaunchedEffect(isliked){
        FirebaseFirestore.getInstance().collection("books").document(mbook.id!!).update("liked",isliked)
    }
    Surface(modifier = Modifier
        .width(220.dp)
        .height(270.dp)
        .padding(10.dp).clickable { oncardclick() }
    , shape = RoundedCornerShape(20.dp), color = Color.DarkGray,
        elevation = 10.dp
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Image(painter = rememberImagePainter(data = mbook.image.toString()), contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(0.50f)
                    .fillMaxHeight(0.50f))
                Column(modifier = Modifier.padding(10.dp)) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription ="",
                    modifier = Modifier
                        .size(26.dp)
                        .padding(0.dp)
                        .clickable { isliked = !isliked },
                        tint = if (isliked) Color(0xFFE06D6D) else Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        modifier = Modifier
                            .height(60.dp)
                            .width(40.dp)
                            .padding(0.dp), elevation = 5.dp,
                        color = Color.DarkGray, shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                            Icon(
                                imageVector = Icons.Default.Star, contentDescription = "",
                                tint = Color.Yellow, modifier = Modifier.padding(2.dp)
                            )
                            Text(text = mbook.rating.toString(), color = Color.LightGray, modifier = Modifier.padding(2.dp))
                            
                        }


                    }

                }

            }
            Text(text = mbook.title.toString(), fontSize = 19.sp, fontWeight = FontWeight.Bold,
            color = Color.LightGray, modifier = Modifier
                    .padding(3.dp),
            maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = mbook.authors.toString(), fontSize = 16.sp,
                color = Color.LightGray,
            maxLines = 1, overflow = TextOverflow.Ellipsis,modifier = Modifier.padding(3.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
            stylebutton(label = buttonlabel, bgcolor = Color.Black, tcolor = Color.White)}


        }

    }
}

@Composable fun Bookrow(book: Item,navcontroller:NavController,onclick:()->Unit){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(5.dp).clickable { onclick() }, elevation = 10.dp, color = Color.DarkGray

    ) {
        Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically) {
            var imagelink=book.volumeInfo.imageLinks


            Image(painter = rememberImagePainter(data = if (imagelink!=null) imagelink.thumbnail else
                ""), contentDescription = "",
            modifier = Modifier
                .width(80.dp)
                .height(100.dp)
                .padding(4.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Column() {
                Text(text = book.volumeInfo.title, fontSize = 19.sp, fontWeight = FontWeight.Bold,
                    color = Color.LightGray, modifier = Modifier
                        .padding(3.dp),
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                var authors=book.volumeInfo.authors
                if (!authors.isNullOrEmpty()){
                Text(text = authors.toString(), fontSize = 16.sp,
                    color = Color.LightGray,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,modifier = Modifier.padding(3.dp))}



            }


        }


    }


}
