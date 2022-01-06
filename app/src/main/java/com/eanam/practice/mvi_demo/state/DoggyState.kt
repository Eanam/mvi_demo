package com.eanam.practice.mvi_demo.state

import com.eanam.practice.mvi_demo.model.entity.Doggy

sealed class DoggyState {
    object Idle: DoggyState()
    object Loading: DoggyState()

    data class Doggys(val data: List<Doggy>): DoggyState()
}