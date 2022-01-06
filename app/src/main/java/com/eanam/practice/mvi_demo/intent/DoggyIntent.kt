package com.eanam.practice.mvi_demo.intent

//定义Intent
sealed class DoggyIntent {

    object FetchDoggy: DoggyIntent()

}