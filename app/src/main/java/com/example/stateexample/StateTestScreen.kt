package com.example.stateexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun StateTestScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyText()
        MyTextField()
    }
}

@Composable
fun MyText(){
    Text(text = "Hello", style = TextStyle(fontSize = 30.sp))
}

@Composable
fun MyTextField(){
    var name = ""
    OutlinedTextField(
        value = name,
        onValueChange = {
            name = it
        },
        label = { Text(text = "Enter Name") }
    )
}