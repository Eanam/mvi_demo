package com.eanam.practice.mvi_demo.model.repository.local

import com.eanam.practice.mvi_demo.model.entity.Doggy
import kotlinx.coroutines.delay

class DoggyRepository {
    private val doggyList by lazy {
        listOf(
            Doggy("", ""),
            Doggy("", "")
        )
    }

    suspend fun fetchDoggy(): List<Doggy> {
        delay(3000)
        return doggyList
    }
}