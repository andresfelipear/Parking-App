package com.vancouverparking.parkingapp2.authentication.di

import com.vancouverparking.parkingapp2.authentication.data.local.repositories.DefaultLocalAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.local.repositories.LocalAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.DefaultRemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.core.di.NetworkModule

object AuthenticationModule
{
    val retrofit = NetworkModule.provideRetrofit()

    fun provideAuthenticationApi(): AuthenticationApi
    {
        return retrofit.create(AuthenticationApi::class.java)
    }

    fun provideRemoteRepository(): RemoteAuthRepository
    {
        return DefaultRemoteAuthRepository()
    }

    fun provideLocalRepository(): LocalAuthRepository
    {
        return DefaultLocalAuthRepository()
    }
}