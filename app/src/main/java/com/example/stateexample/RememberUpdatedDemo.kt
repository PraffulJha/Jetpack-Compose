package com.example.stateexample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.delay


@Composable
fun RememberUpdatedDemo(
    onTimeOut : () -> Unit
) {

    val updatedOnTimeOut by rememberUpdatedState(onTimeOut)

    LaunchedEffect(
        true
    ) {
        delay(3000L)
        updatedOnTimeOut()
    }
}