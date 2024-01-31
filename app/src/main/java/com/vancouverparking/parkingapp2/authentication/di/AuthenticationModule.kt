package com.vancouverparking.parkingapp2.authentication.di

import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object AuthenticationModule
{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://regres.in")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideRetrofit(): Retrofit
    {
        return retrofit
    }

    fun provideApiService(): AuthenticationApi
    {
        return retrofit.create(AuthenticationApi::class.java)
    }
}