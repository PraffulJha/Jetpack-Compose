package com.example.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.stateexample.viewmodel.FlowsViewmodel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel = ViewModelProvider(this)[FlowsViewmodel::class.java]
        setContent {
            HomeScreen(viewmodel = viewmodel)
        }
    }

    @Composable
    fun HomeScreen(modifier: Modifier = Modifier,viewmodel: FlowsViewmodel){
        val textLiveData by viewmodel.liveData.observeAsState("")
        val textState by viewmodel.stateFlow.collectAsState()
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val dialogEvent = remember { mutableStateOf(false) }
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(10.dp).background(
            Color.White)) {
            Text("Hi There")
            Button(onClick = {
                // update livedata
                viewmodel.updateLiveData()

            }){
                Text(text = textLiveData, fontSize = 20.sp)
            }
            Text(text = textState, fontSize =  20.sp)
            Button(onClick = {
                // update livedata
                viewmodel.updateStateFlow()
            }){
                Text("click me")
            }
            Text("Hi There")
            LaunchedEffect(Unit) {
                viewmodel.sharedFlow.collect {
                    dialogEvent.value = true
                }
            }
            Button(onClick = {
                viewmodel.showDialog()
            }){
                Text("click me")
            }
        }

    }
}

