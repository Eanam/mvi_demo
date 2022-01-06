package com.eanam.practice.mvi_demo.model.repository.network

import com.eanam.practice.mvi_demo.model.entity.Doggy
import retrofit2.http.GET

interface DoggyService {

    @GET()
    suspend fun fetchAllDoggy(): List<Doggy>
}