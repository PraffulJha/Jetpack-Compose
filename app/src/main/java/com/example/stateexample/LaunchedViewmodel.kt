package com.example.stateexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LaunchedViewmodel : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<ScreenEvents>()

    val sharedFlow : SharedFlow<ScreenEvents> = _sharedFlow

    init {
        viewModelScope.launch {
            _sharedFlow.emit(ScreenEvents.ShowSnackBar("Hello World"))
        }
    }

    sealed class ScreenEvents{
        data class ShowSnackBar(val message  : String) : ScreenEvents()

        data class  Navigate(val route : String ) : ScreenEvents()
    }
}

/****/


