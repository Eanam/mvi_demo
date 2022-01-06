package com.eanam.practice.mvi_demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.eanam.practice.mvi_demo.intent.DoggyIntent
import com.eanam.practice.mvi_demo.model.entity.Doggy
import com.eanam.practice.mvi_demo.state.DoggyState
import com.eanam.practice.mvi_demo.ui.theme.Mvi_demoTheme
import com.eanam.practice.mvi_demo.viewmodel.DoggyViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vm by viewModels<DoggyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mvi_demoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DoggyActivity(vm = vm)
                }
            }
        }

        lifecycleScope.launch {
            vm.doggyIntent.send(DoggyIntent.FetchDoggy)
        }
    }
}

@Composable
fun DoggyActivity(
    vm: DoggyViewModel
) {
    val state = vm.state
    DoggyScreen(state = state.value)
}

@Composable
fun DoggyScreen(
    state: DoggyState
) {
    Log.d("cmoigo", "DoggyScreen: $state")
    when(state) {
        is DoggyState.Loading -> LoadingCompose()
        is DoggyState.Doggys -> DoggyList(data = state.data)
    }
}

@Composable
fun LoadingCompose() {
    CircularProgressIndicator()
}

@Composable
fun DoggyList(data: List<Doggy>) {
    Column {
        data.forEachIndexed { index, item ->
            Text(text = "$index")
        }
    }
}