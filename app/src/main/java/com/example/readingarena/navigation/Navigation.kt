package com.example.readingarena.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readingarena.bookviewmodels.Bookidviewmodel
import com.example.readingarena.screens.*
import com.example.readingarena.bookviewmodels.Bookviewmodel
import com.example.readingarena.bookviewmodels.Udetailsviewmodel
import com.example.readingarena.bookviewmodels.Userbooksviewmodel
import com.example.readingarena.loginviewmodel.Loginviewmodel

@Composable
fun navigation(){
    val udetailsviewmodel= hiltViewModel<Udetailsviewmodel>()
    val bookviewmodel= hiltViewModel<Bookviewmodel>()
    val loginviewmodel= viewModel<Loginviewmodel>()
    val navcontroller= rememberNavController()
    val bookidviewmodel= hiltViewModel<Bookidviewmodel>()
    val ubooksviewmodel= hiltViewModel<Userbooksviewmodel>()
    NavHost(navController = navcontroller, startDestination = Readerscreens.Splashscreen.name){
        composable(route = Readerscreens.Splashscreen.name){
splashscreen(navcontroller = navcontroller)
        }
        composable(route =Readerscreens.Homescreen.name){
            homescreen(navcontroller = navcontroller, ubooksviewmodel = ubooksviewmodel, udetailsviewmodel = udetailsviewmodel)

        }
        composable(route=Readerscreens.Loginscreen.name){
            loginscreen(navcontroller=navcontroller, loginviewmodel = loginviewmodel, udetailsviewmodel = udetailsviewmodel)
        }
        composable(route = Readerscreens.Sinupscreen.name){
            sinupscreen(navcontroller, loginviewmodel = loginviewmodel, udetailsviewmodel = udetailsviewmodel)
        }
        composable(route = Readerscreens.Searchscreen.name){

            searchscreen(navcontroller = navcontroller,bookviewmodel)
        }
        composable(route="${Readerscreens.Detailscreen.name}/{bookid}",
        arguments = listOf(navArgument(name = "bookid"){type= NavType.StringType})
        ){
            detailscreen(navcontroller=navcontroller, bookid = it.arguments?.getString("bookid"), bookidviewmodel = bookidviewmodel)
        }
        composable(route="${Readerscreens.Updatescreen.name}/{bookid}", arguments = listOf(
            navArgument(name = "bookid"){type= NavType.StringType}
        )){
            updatescreen(navcontroller = navcontroller, bookid =it.arguments?.getString("bookid") , userbooksviewmodel = ubooksviewmodel)

        }
        composable(route=Readerscreens.Statscreen.name){
            statscreen(navcontroller = navcontroller, ubooksviewmodel = ubooksviewmodel, udetailsviewmodel = udetailsviewmodel)

        }
        composable(route=Readerscreens.Likedscreen.name){
            likedscreen(navcontroller = navcontroller, ubooksviewmodel = ubooksviewmodel)

        }


    }

}