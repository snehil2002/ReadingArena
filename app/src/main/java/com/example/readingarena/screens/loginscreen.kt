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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readingarena.R
import com.example.readingarena.bookviewmodels.Udetailsviewmodel
import com.example.readingarena.screencomponents.screencomponents.normaltextfield
import com.example.readingarena.screencomponents.screencomponents.passfield
import com.example.readingarena.screencomponents.submitbutton
import com.example.readingarena.utils.toastmessagel
import com.example.readingarena.loginviewmodel.Loginviewmodel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun loginscreen(navcontroller:NavController,loginviewmodel: Loginviewmodel,udetailsviewmodel: Udetailsviewmodel){
    val context= LocalContext.current

    var emailinput= rememberSaveable {
        mutableStateOf("")
    }
    var passinput=rememberSaveable{
        mutableStateOf("")
    }
    var passvisvibility=rememberSaveable{
        mutableStateOf(false)
    }
    var passfocus=FocusRequester()
    val keyboardcontroller=LocalSoftwareKeyboardController.current
    var valid=if (emailinput.value.trim().isNotEmpty() && passinput.value.trim().isNotEmpty()) true else false

    Column(modifier = Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Reading Arena", fontSize = 30.sp,
            color = Color.LightGray, fontFamily = FontFamily(Font(R.font.pacificofont)),
            modifier=Modifier.padding(10.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


            Text(
                text = "Login",
                fontSize = 30.sp,
                color = Color.LightGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            normaltextfield(textvalue =emailinput , singleline =true , label ="Email", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), keyboardtype = KeyboardType.Email, onaction = {passfocus.requestFocus()})
passfield(passvalue = passinput, singleline = true, imeaction = ImeAction.Done, label = "Password", passvisibility = passvisvibility, modifier = Modifier
    .fillMaxWidth()
    .padding(10.dp)
    .focusRequester(passfocus), onaction = { keyboardcontroller?.hide() })
            submitbutton(label = "Login", modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(
                    CircleShape
                ), buttoncolor = if (valid) Color.Black else Color.LightGray,
            tcolor =if (valid) Color.White else Color.Black ){
                if(valid){
                loginviewmodel.login(emailinput.value,passinput.value, success = {
                                                                                 toastmessagel(
                                                                                     context = context,
                                                                                     message = "Succesfully Logged In"
                                                                                 )
                    udetailsviewmodel.getuserdetails(FirebaseAuth.getInstance().currentUser!!.uid)
                    navcontroller.navigate(Readerscreens.Homescreen.name){
                        popUpTo(Readerscreens.Splashscreen.name){
                            inclusive=false
                        }
                    }
                },
                failure = { toastmessagel(context = context, message ="Invalid Email or Password" )})}
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.Center) {
                Text(text = "New User? ", color = Color.White, fontSize = 20.sp)
                Text(text = "Sinup", fontWeight = FontWeight.Bold, color = Color.Cyan, fontSize = 20.sp,
                modifier = Modifier.clickable { navcontroller.navigate(Readerscreens.Sinupscreen.name) })


            }



        }




    }
}