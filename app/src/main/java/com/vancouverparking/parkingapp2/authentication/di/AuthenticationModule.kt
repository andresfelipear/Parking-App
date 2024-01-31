package com.vancouverparking.parkingapp2.authentication.di

import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import com.vancouverparking.parkingapp2.core.di.NetworkModule

object AuthenticationModule
{
    val retrofit = NetworkModule.provideRetrofit()

    fun provideAuthenticationApi(): AuthenticationApi
    {
        return retrofit.create(AuthenticationApi::class.java)
    }
}