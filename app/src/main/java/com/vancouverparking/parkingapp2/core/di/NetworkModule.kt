package com.vancouverparking.parkingapp2.core.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkModule
{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://regres.in")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideRetrofit(): Retrofit
    {
        return retrofit
    }

}