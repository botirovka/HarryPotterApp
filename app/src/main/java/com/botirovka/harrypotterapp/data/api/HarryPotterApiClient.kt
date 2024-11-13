package com.botirovka.harrypotterapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HarryPotterApiClient
{
    private const val BASE_URL = "https://hp-api.onrender.com/api/"

    val retrofit: HarryPotterApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HarryPotterApiService::class.java)
    }
}