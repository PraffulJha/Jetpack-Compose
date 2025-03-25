package com.example.stateexample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LaunchedEffectDemo (viewmodel: LaunchedViewmodel){

    LaunchedEffect(true) {
        viewmodel.sharedFlow.collect { event ->
            when(event) {
                is LaunchedViewmodel.ScreenEvents.ShowSnackBar -> {}

                is LaunchedViewmodel.ScreenEvents.Navigate -> {}
            }

        }
    }
}

/***
 * passed key as true so it actually called once . so the first time when composable is composable
 * this launched effect block will executing and never cancelled and relaunched unless the composable
 * actually leaves the composition
 *
 * So we are safe to collect the shared flow because this is now never executed with every recomposition
 * instead only the first time this composable actually enter the screens
 *
 *
 * */
