package com.eanam.practice.mvi_demo.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eanam.practice.mvi_demo.intent.DoggyIntent
import com.eanam.practice.mvi_demo.model.entity.Doggy
import com.eanam.practice.mvi_demo.model.repository.local.DoggyRepository
import com.eanam.practice.state_widget.PageData
import com.eanam.practice.state_widget.PageState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class DoggyViewModel: ViewModel() {

    val doggyIntent = Channel<DoggyIntent>(Channel.UNLIMITED)

    var state = mutableStateOf<PageState<List<Doggy>>>(PageState(PageData.Loading))
        private set

    private val repository by lazy { DoggyRepository() }

    init {
        viewModelScope.launch {
            doggyIntent.consumeAsFlow().collect {
                Log.d("cmoigo", "intent is $it")
                when(it) {
                    is DoggyIntent.FetchDoggy -> fetchDoggy()
                }
            }
        }
    }

    private fun fetchDoggy() {
        viewModelScope.launch {
            state.value = PageState(PageData.Success(repository.fetchDoggy()))
            Log.d("cmoigo", "fetchDoggy: ${state.value}")
        }
    }
}