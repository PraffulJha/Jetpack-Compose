package com.example.stateexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlowsViewmodel : ViewModel() {


    private val _mutableLiveData = MutableLiveData<String>("Hi There")
    val liveData : LiveData<String> = _mutableLiveData

    private  val _mutableSharedFlow = MutableSharedFlow<String>()
    val sharedFlow : SharedFlow<String> = _mutableSharedFlow.asSharedFlow()

    private  val _mutableStateFlow = MutableStateFlow<String>("Test")
    val stateFlow : StateFlow<String> = _mutableStateFlow.asStateFlow()

    fun updateLiveData(){
        _mutableLiveData.value = "newString"
    }
    fun updateStateFlow(){
        _mutableStateFlow.value = "New StateFlow"
    }
    fun showDialog(){
        viewModelScope.launch {
            _mutableSharedFlow.emit("Message")
        }
    }


}