package com.example.readingarena.screencomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun floatingbutton(onclick:()->Unit){
    FloatingActionButton(onClick = onclick,
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        shape = CircleShape, backgroundColor = Color.Cyan) {

        Icon(imageVector = Icons.Default.Add, contentDescription = "",
            tint = Color.Black)

    }

}

@Composable fun submitbutton(label:String, modifier: Modifier=Modifier, tcolor: Color= Color.Black, buttoncolor: Color= Color.Black, onclick:()->Unit={}){
    Button(onClick = {onclick()}, modifier =modifier, colors = ButtonDefaults.buttonColors(backgroundColor = buttoncolor, contentColor = Color.DarkGray)) {
        Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Light, color = tcolor)

    }
}

@Composable fun stylebutton(label: String, modifier: Modifier=Modifier, bgcolor:Color= Color.Black, tcolor:Color= Color.White, onclick: () -> Unit={}){
    Button(onClick = { onclick() }, modifier = modifier
        .height(35.dp),
    shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
    , colors = ButtonDefaults.buttonColors(backgroundColor = bgcolor)) {
        Text(text = label, fontSize = 13.sp, color = tcolor,
        fontWeight = FontWeight.Light)

    }

}

@Composable fun textbutton(label:String,modifier: Modifier=Modifier,onclick:()->Unit={}){
    Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Cyan,
    modifier = modifier.clickable { onclick() })
}