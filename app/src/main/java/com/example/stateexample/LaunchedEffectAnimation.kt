package com.example.stateexample


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun LaunchedEffectAnimation(
    counter : Int
) {
    val animatable  = remember{
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(counter) {
        animatable.animateTo(counter.toFloat())
    }
}