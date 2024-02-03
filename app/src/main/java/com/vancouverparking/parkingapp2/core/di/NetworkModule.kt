package com.vancouverparking.parkingapp2.core.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkModule
{
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    fun provideRetrofit(): Retrofit
    {
        return retrofit
    }

}