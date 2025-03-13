package com.example.stateexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodels.StateViewmodel


@Composable
fun StateTestScreen(viewmodel: StateViewmodel){
    val password  by viewmodel.password.observeAsState("")
    var name by remember {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyText(name)
        MyTextField(name, onNameChange = {
            name = it
        },
            onEmailChange = {
                email = it
            }, email,
            password, onPasswordChange = {
                viewmodel.updateName(it)
            })
    }
}

@Composable
fun MyText(name : String){
    Text(text = "Hello $name", style = TextStyle(fontSize = 30.sp))
}

@Composable
fun MyTextField(name : String,onNameChange : (String) -> Unit, onEmailChange : (String)-> Unit,email : String,password :String,onPasswordChange : (String) -> Unit){

    OutlinedTextField(
        value = name,
        onValueChange = {
            onNameChange(it)
        },
        label = { Text(text = "Enter Name") }
    )
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp))

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = { Text(text = "Enter Email") }
    )
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp))

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = { Text(text = "Enter Password") }
    )
}