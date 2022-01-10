package com.eanam.practice.mvi_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eanam.practice.mvi_demo.intent.DoggyIntent
import com.eanam.practice.mvi_demo.model.entity.Doggy
import com.eanam.practice.mvi_demo.ui.theme.Mvi_demoTheme
import com.eanam.practice.mvi_demo.viewmodel.DoggyViewModel
import com.eanam.practice.state_widget.ComposeState

class MainActivity : ComponentActivity() {

    private val vm by viewModels<DoggyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mvi_demoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colors.background
                ) {
                    DoggyActivity(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        vm = vm
                    )
                }
            }
        }
    }
}

@Composable
fun DoggyActivity(
    modifier: Modifier,
    vm: DoggyViewModel
) {
    Box(
        modifier = Modifier.width(200.dp).height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        ComposeState(
            modifier = modifier,
            pageState = vm.state.value,
            loading = { vm.doggyIntent.send(DoggyIntent.FetchDoggy) },
            loadingComponentBlock = { LoadingCompose() },
            emptyComponentBlock = null,
            errorComponentBlock = null,
        ) {
            DoggyList(data = it.data ?: emptyList())
        }
    }
}


@Composable
fun LoadingCompose() {
    CircularProgressIndicator()
}

@Composable
fun DoggyList(data: List<Doggy>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        data.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                text = "$index"
            )
        }
    }
}