package com.example.readingarena.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readingarena.screencomponents.screencomponents.normaltextfield
import com.example.readingarena.bookviewmodels.Bookviewmodel


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun searchscreen(navcontroller:NavController, bookviewmodel:Bookviewmodel){
    Scaffold(topBar = { topappbar(title = "Search Books", ishome = false , onback = {navcontroller.navigate(Readerscreens.Homescreen.name)})}) {

        var searchquery= rememberSaveable{
            mutableStateOf("")
        }


        var allbooks =bookviewmodel.allbooks


        val keyboardcontoller=LocalSoftwareKeyboardController.current




        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            normaltextfield(textvalue = searchquery, singleline = true, label ="Search",
            modifier = Modifier.fillMaxWidth(), imeaction = ImeAction.Search){
                keyboardcontoller?.hide()
                bookviewmodel.getallbook(searchquery.value)



            }
            Spacer(modifier = Modifier.height(60.dp))

            if (bookviewmodel.isloading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

            }
            else if (allbooks.isNullOrEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No books found",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp),
                        color = Color(0xFF009688)
                    )

                }
            }
            else{


                LazyColumn(){
                    items(allbooks!!){
                        Bookrow(book = it, navcontroller =navcontroller, onclick = {navcontroller.navigate("${Readerscreens.Detailscreen.name}/${it.id}")} )

                    }
                }}






        }

    }

}