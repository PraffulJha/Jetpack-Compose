package com.example.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateViewmodel : ViewModel() {

    private var _password  = MutableLiveData<String>()
    val password  : LiveData<String> = _password

    fun updateName(newPassword : String){
        _password.value = newPassword
    }
}