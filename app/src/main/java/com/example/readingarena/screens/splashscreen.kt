package com.example.readingarena.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readingarena.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable fun splashscreen(navcontroller:NavController){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        var scale=remember{
            androidx.compose.animation.core.Animatable(0f)
        }
        LaunchedEffect(key1 = true ){
            scale.animateTo(targetValue = 0.8f, animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(8f).getInterpolation(it)



            }))
            delay(1600L)
            if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navcontroller.navigate(Readerscreens.Loginscreen.name)}
            else{
                navcontroller.navigate(Readerscreens.Homescreen.name)}

        }
        Surface(modifier = Modifier
            .size(350.dp)
            .scale(scale.value), shape = CircleShape, color = MaterialTheme.colors.background,
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = R.drawable.menubook), contentDescription = "",
                modifier = Modifier.size(50.dp), tint = Color.LightGray)
                Text(text = "Reading Arena", fontSize = 40.sp,
                color = Color.LightGray, fontFamily = FontFamily(Font(R.font.pacificofont))
                )
                Text(text = "Read , Learn & Enjoy", fontSize = 20.sp,
                    color = Color.LightGray
                )


            }
            
        }
    }
    
}