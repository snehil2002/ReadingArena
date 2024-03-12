package com.example.readingarena.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readingarena.bookdata.Item
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.bookviewmodels.Bookidviewmodel
import com.example.readingarena.dataorexception.DataorException
import com.example.readingarena.screencomponents.stylebutton
import com.example.readingarena.utils.toastmessages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable fun detailscreen(navcontroller:NavController,bookid:String?, bookidviewmodel:Bookidviewmodel){
    val context= LocalContext.current

    val bookbyid= produceState(initialValue = DataorException<Item>(loading = true)){
        value= bookidviewmodel.getbookbyid(bookid = bookid.toString())!!
    }
    if (bookbyid.value.data!=null)
        bookbyid.value.loading=false
    Scaffold(topBar = {topappbar(title = "Book Details", ishome = false, onback = {navcontroller.popBackStack()})}) {
        if(bookbyid.value.loading==true)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        else{
            var detailbook=bookbyid.value.data!!
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(30.dp))
                Surface(modifier = Modifier.size(120.dp), shape = CircleShape, color = Color.DarkGray, elevation = 0.dp) {
                    Image(painter = rememberImagePainter(data = detailbook.volumeInfo.imageLinks.thumbnail), contentDescription ="" )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = detailbook.volumeInfo.title, color = Color.LightGray,
                fontWeight = FontWeight.Bold, fontSize = 22.sp, textAlign = TextAlign.Center)
                Text(text = "Authors: ${detailbook.volumeInfo.authors}",
                color = Color.LightGray, fontSize = 17.sp,textAlign = TextAlign.Center)
                Text(text = "Page Count: ${detailbook.volumeInfo.pageCount}",
                    color = Color.LightGray, fontSize = 17.sp,textAlign = TextAlign.Center)
                Text(text = "Categories: ${detailbook.volumeInfo.categories}",
                    color = Color.LightGray, maxLines = 4, overflow = TextOverflow.Ellipsis, fontSize = 17.sp,textAlign = TextAlign.Center)
                Text(text = "Published: ${detailbook.volumeInfo.publishedDate}",
                    color = Color.LightGray, fontSize = 17.sp,textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(10.dp))
                val cleandescription=HtmlCompat.fromHtml(detailbook.volumeInfo.description,FROM_HTML_MODE_LEGACY).toString()
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, color = Color.LightGray)){
                    item {
                        Text(text = cleandescription,
                            color = Color.LightGray, fontSize = 17.sp,textAlign = TextAlign.Justify, modifier = Modifier.padding(5.dp))

                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    stylebutton(label = "Save", modifier = Modifier.width(80.dp), onclick = {
                        var book=Mbook(userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                            googleBookId = detailbook.id,
                            title = detailbook.volumeInfo.title,
                            authors = detailbook.volumeInfo.authors.toString(),
                            image = detailbook.volumeInfo.imageLinks.thumbnail,
                            notes="",
                            pageCount = detailbook.volumeInfo.pageCount.toString(),
                            publishedDate = detailbook.volumeInfo.publishedDate,
                            rating = 0.0,
                            description = detailbook.volumeInfo.description,
                            liked = false
                        )
                        savetofirebase(book,navcontroller,context)

                    })
                    stylebutton(label = "Cancel", modifier = Modifier.width(80.dp), onclick = {navcontroller.popBackStack()})
                    
                }


            }
        }


    }

}


fun savetofirebase(book: Mbook,navcontroller: NavController,context:Context){


    val db=FirebaseFirestore.getInstance()
    val bookcollection=db.collection("books")
    bookcollection.add(book).addOnSuccessListener {
        bookcollection.document(it.id).update("id",it.id).addOnCompleteListener{
            if(it.isSuccessful){
                navcontroller.popBackStack()
                toastmessages(context,"Book Saved")
            }
        }.addOnFailureListener {
            toastmessages(context,"Failed to save")
        }
    }
}