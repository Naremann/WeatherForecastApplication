package com.example.weatherforecastapplication.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofit by lazy {
        //customize network requests
        val client=OkHttpClient.Builder().build()
        Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}