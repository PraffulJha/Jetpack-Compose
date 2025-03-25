package com.example.stateexample.rememberCoroutine

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RemeberCoroutineDemo() {
    val scope = rememberCoroutineScope() // 👈 get the reference of coroutine scope

    Button( onClick = {
        scope.launch {
            delay(1000L)
            println("Hello World")
        }
    }
    ) { }
}
// once the composable leaves all the coroutine scope will be cancelled
// we can use it for any db call or network call
// Note only use in callback I mean it will be like onclick
// not required to do with actual composable
//  if we do it will be side effect
// no that required