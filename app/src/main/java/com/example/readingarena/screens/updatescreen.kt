package com.example.readingarena.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readingarena.bookdata.Mbook
import com.example.readingarena.bookviewmodels.Userbooksviewmodel
import com.example.readingarena.screencomponents.screencomponents.normaltextfield
import com.example.readingarena.screencomponents.stylebutton
import com.example.readingarena.screencomponents.textbutton
import com.example.readingarena.utils.timetodate
import com.example.readingarena.utils.toastmessages
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable fun updatescreen(navcontroller:NavController, bookid: String?, userbooksviewmodel: Userbooksviewmodel){
    val context=LocalContext.current
    Scaffold(topBar = { topappbar(title = "Update Book", ishome = false, onback = {navcontroller.popBackStack()})}) {
        if(userbooksviewmodel.userbooks.value.loading==true){
            LinearProgressIndicator()
        }
        else{
            val keyboardcontroller=LocalSoftwareKeyboardController.current
            val booklist=userbooksviewmodel.userbooks.value.data!!.filter {
                it.id==bookid
            }
            if(!booklist.isNullOrEmpty()){
            val book=booklist[0]

            var notes= rememberSaveable() {
                mutableStateOf(book.notes.toString())
            }
            var rating= rememberSaveable{
                mutableStateOf(book.rating!!.toInt())
            }
            var updatestate= rememberSaveable() {
                mutableStateOf(false)
            }

            var startstate= rememberSaveable() {
                mutableStateOf(false)
            }
            var finishstate= rememberSaveable() {
                mutableStateOf(false)
            }
            var started=if (startstate.value)Timestamp.now() else book.startedReading
            var finished=if (finishstate.value)Timestamp.now() else book.finishedReading

            var alertboxstate= rememberSaveable{
                mutableStateOf(false)
            }
            if(alertboxstate.value){
                AlertDialog(onDismissRequest ={alertboxstate.value=false},
                    buttons = {
                        Row(modifier=Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {
                            textbutton(label = "OK"){
                                navcontroller.popBackStack()
                                FirebaseFirestore.getInstance().collection("books").document(bookid!!).delete().addOnSuccessListener {
                                    alertboxstate.value=false

                                    toastmessages(context,"Book Deleted")




                                }.addOnFailureListener { toastmessages(context, "Error in Deleting") }
                            }
                            textbutton(label = "CANCEL"){
                                alertboxstate.value=false
                            }
                        }
                    },
                title = { Text(text = "Delete Book")},
                text = { Text(text = "Are you sure you want to delete this book ?")},
                backgroundColor = Color.DarkGray, contentColor = Color.LightGray)
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                booksurface(book)
                Spacer(modifier = Modifier.height(20.dp))
                normaltextfield(onaction = {keyboardcontroller!!.hide()
                                           updatestate.value=true},textvalue =notes , singleline = false, label = "Enter Your Thoughts", modifier = Modifier
                    .height(125.dp)
                    .width(350.dp)
                    .padding(5.dp))
                Spacer(modifier = Modifier.height(30.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    if (book.startedReading==null){
                    textbutton(label = if(startstate.value) "Started Reading" else "Start Reading"){
                        startstate.value=!startstate.value

                    } }
                    else{
                        Text(text = "Started On: ${timetodate(book.startedReading!!) }",fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
                    }
                    if (book.finishedReading==null){
                        textbutton(label = if(finishstate.value) "Finished Reading" else "Mark As Read"){
                            finishstate.value=!finishstate.value
                        } }
                    else{
                        Text(text = "Finished On: ${timetodate(book.finishedReading!!)}",fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.LightGray)
                    }

                    
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Rating", fontSize = 18.sp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(10.dp))
                Row(){
                    for(i in 1..5){
                        Icon(imageVector = Icons.Default.Star, contentDescription ="",
                        tint = if(rating.value>=i)Color.Yellow else Color.LightGray, modifier = Modifier
                                .size(35.dp)
                                .clickable {
                                    updatestate.value = true
                                    if (rating.value == i) rating.value-- else rating.value = i
                                })
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                    stylebutton(label = "UPDATE"){
                        if(updatestate.value || startstate.value || finishstate.value){
                            val updatebook= hashMapOf(
                                "rating" to rating.value,
                                "notes" to notes.value,
                                "started_reading" to started,
                                "finished_reading" to finished
                            ).toMap()
                            FirebaseFirestore.getInstance().collection("books").document("$bookid").update(updatebook).addOnSuccessListener {
                                toastmessages(context,"Book Updated")
                            }.addOnFailureListener{
                                toastmessages(context,"Error in Updating")

                            }

                        }

                    }
                    stylebutton(label = "DELETE"){
                        alertboxstate.value=true
                    }
                }



            }

        }}

    }



}

@Composable fun booksurface(book:Mbook){
    Surface(modifier = androidx.compose.ui.Modifier
        .fillMaxWidth()
        .height(140.dp),
    shape = RoundedCornerShape(100), elevation = 5.dp, color = Color.DarkGray
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
            Image(painter = rememberImagePainter(data = book.image.toString()), contentDescription ="" ,
            modifier = Modifier
                .height(110.dp)
                .width(130.dp)
                .clip(shape = RoundedCornerShape(topStart = 130.dp, topEnd = 30.dp)))
            Column(modifier = Modifier
                .fillMaxHeight()
                .width(150.dp), verticalArrangement = Arrangement.Center) {
                Text(text = book.title.toString(), fontSize = 23.sp, fontWeight = FontWeight.Bold,
                maxLines = 2, overflow = TextOverflow.Ellipsis, color = Color.LightGray)
                Text(text = book.authors.toString(), fontSize = 15.sp, fontWeight = FontWeight.Normal,
                    maxLines = 2, overflow = TextOverflow.Ellipsis,color = Color.LightGray)
                Text(text = book.publishedDate.toString(), fontSize = 15.sp, fontWeight = FontWeight.Normal,
                    maxLines = 2, overflow = TextOverflow.Ellipsis,color = Color.LightGray)



            }

        }

    }

}

