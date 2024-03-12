package com.example.readingarena.screencomponents.screencomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readingarena.R

@Composable fun normaltextfield(
    textvalue:MutableState<String>,singleline:Boolean,modifier:Modifier=Modifier,
    label:String,keyboardtype:KeyboardType= KeyboardType.Text,imeaction:ImeAction= ImeAction.Next,
    onaction:()->Unit={}
){
    OutlinedTextField(value = textvalue.value, onValueChange ={
        textvalue.value=it
    }, enabled = true, singleLine = singleline,
    modifier = modifier, textStyle = TextStyle(fontSize = 20.sp),
        label={ Text(text = label, fontSize = 17.sp)}, keyboardOptions = KeyboardOptions(
            keyboardType = keyboardtype, imeAction = imeaction
        ), keyboardActions = KeyboardActions(onAny = {onaction()}),
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White,
        cursorColor = Color.LightGray, unfocusedIndicatorColor = Color.LightGray, focusedLabelColor = Color.LightGray,
        unfocusedLabelColor = Color.LightGray)

    )


}

@Composable fun passfield(
    passvalue:MutableState<String>,singleline:Boolean,modifier:Modifier=Modifier,
    label:String,imeaction:ImeAction= ImeAction.Next,
    onaction:()->Unit={},
    passvisibility:MutableState<Boolean>
){
    OutlinedTextField(value = passvalue.value, onValueChange ={
        passvalue.value=it
    }, enabled = true, singleLine = singleline,
        modifier = modifier, textStyle = TextStyle(fontSize = 20.sp),
        label={ Text(text = label, fontSize = 17.sp)}, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = imeaction
        ), keyboardActions = KeyboardActions(onAny = {onaction()}),
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White,
            cursorColor = Color.LightGray, unfocusedIndicatorColor = Color.LightGray, focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray, trailingIconColor = Color.LightGray),
        visualTransformation =if (passvisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (passvisibility.value)
            Icon(painter = painterResource(id = R.drawable.visibleon), contentDescription = "", tint = Color.Cyan,
            modifier = Modifier.clickable { passvisibility.value=false })
        else Icon(painter = painterResource(id = R.drawable.visibleoff), contentDescription = "",tint= Color.LightGray,
            modifier = Modifier.clickable { passvisibility.value=true })}

    )


}
