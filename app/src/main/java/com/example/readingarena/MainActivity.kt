package com.example.readingarena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.readingarena.navigation.navigation
import com.example.readingarena.ui.theme.ReadingArenaTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Myapp()

        }
    }}



@Composable fun Myapp() {
    MaterialTheme(colors = lightColors(primary = Color.LightGray, background = Color.DarkGray)){
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
navigation()

        }
    }
    }


