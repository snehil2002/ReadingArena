package com.example.readingarena.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readingarena.R
import com.example.readingarena.bookviewmodels.Udetailsviewmodel
import com.example.readingarena.screencomponents.screencomponents.normaltextfield
import com.example.readingarena.screencomponents.screencomponents.passfield
import com.example.readingarena.screencomponents.submitbutton
import com.example.readingarena.userdata.Muser
import com.example.readingarena.utils.toastmessagel
import com.example.readingarena.loginviewmodel.Loginviewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun sinupscreen(navcontroller:NavController,loginviewmodel: Loginviewmodel,udetailsviewmodel: Udetailsviewmodel){

    val context= LocalContext.current
    val emailregex="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$"

    var usernameinput=rememberSaveable{ mutableStateOf("") }
    var emailinput=rememberSaveable{ mutableStateOf("") }
    var passinput=rememberSaveable{ mutableStateOf("") }
    var repassinput=rememberSaveable{ mutableStateOf("") }
    var passvisbility=rememberSaveable{ mutableStateOf(false) }
    var repassvisibility=rememberSaveable{ mutableStateOf(false) }
    val emailfocus=FocusRequester()
    val passfocus=FocusRequester()
    val repassfocus=FocusRequester()
    val keyboardcontroller=LocalSoftwareKeyboardController.current
    var valid=if (usernameinput.value.trim().isNotEmpty() && emailinput.value.trim().isNotEmpty() &&
            passinput.value.trim().isNotEmpty() && repassinput.value.trim().isNotEmpty()) true else false

    Column(modifier = Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Reading Arena", fontSize = 30.sp,
            color = Color.LightGray, fontFamily = FontFamily(Font(R.font.pacificofont)),
            modifier= Modifier.padding(10.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


            Text(
                text = "Signup",
                fontSize = 30.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            normaltextfield(textvalue =usernameinput , singleline =true , label ="Username",modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) , onaction = {emailfocus.requestFocus()})
            normaltextfield(textvalue = emailinput, singleline =true , label ="Email",
            keyboardtype = KeyboardType.Email,modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp).focusRequester(emailfocus),
            onaction = {passfocus.requestFocus()})
            passfield(passvalue = passinput, singleline = true, label = "Password", passvisibility =passvisbility,modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp).focusRequester(passfocus),
            onaction = {repassfocus.requestFocus()})
            passfield(passvalue = repassinput, singleline =true , label ="Re-password" , passvisibility = repassvisibility,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp).focusRequester(repassfocus),
            onaction = {keyboardcontroller?.hide()})
            submitbutton(label = "Signup", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(
                    CircleShape
                ), buttoncolor = if (valid) Color.Black else Color.LightGray,
                tcolor =if (valid) Color.White else Color.Black ){
                if(valid){
                    if (passinput.value.trim().length<6){
                        toastmessagel(context,"Password should have 6 characters atleast")
                    }
                    else if(emailinput.value.trim().matches(emailregex.toRegex())==false){
                        toastmessagel(context,"Enter a valid Email")

                    }

                    else if(passinput.value!=repassinput.value){
                        toastmessagel(context=context, message = "Password & Repassword are not same!")

                    }

                    else{
                        loginviewmodel.sinin(emailinput.value,passinput.value,
                            success = { toastmessagel(context,"Successfully Signed Up")
                                udetailsviewmodel.getuserdetails(FirebaseAuth.getInstance().currentUser!!.uid)
                                navcontroller.navigate(Readerscreens.Homescreen.name){
                                    popUpTo(Readerscreens.Splashscreen.name){
                                        inclusive=false
                                    }
                                }
                                      createuserdb(username=usernameinput.value) },
                            failure = { toastmessagel(context,"Email Already in Use") })
                    }

                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.Center) {
                Text(text = "Already An User? ", color = Color.White, fontSize = 20.sp)
                Text(text = "Login", fontWeight = FontWeight.Bold, color = Color.Cyan, fontSize = 20.sp,
                    modifier = Modifier.clickable { navcontroller.navigate(Readerscreens.Loginscreen.name) })


            }
}}}

fun createuserdb(username:String){
    val userid= FirebaseAuth.getInstance().currentUser?.uid.toString()
    var user= Muser(id=null,uid=userid,username=username,
    quote="live and let live", profession = "teacher", avatarurl = "avatar.jpg").map()


    FirebaseFirestore.getInstance().collection("users").document(userid).set(user)

}
