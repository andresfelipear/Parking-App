package com.vancouverparking.parkingapp2.authentication.di

import com.vancouverparking.parkingapp2.BuildConfig
import com.vancouverparking.parkingapp2.authentication.data.local.daos.UserDao
import com.vancouverparking.parkingapp2.authentication.data.local.repositories.DefaultLocalAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.local.repositories.LocalAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.api.AuthenticationApi
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.DefaultRemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.data.remote.repositories.RemoteAuthRepository
import com.vancouverparking.parkingapp2.authentication.mockdata.MockAuthRepository
import com.vancouverparking.parkingapp2.core.di.NetworkModule

object AuthenticationModule
{
    private val retrofit = NetworkModule.provideRetrofit()
    private val database = DatabaseModule.provideParkingAppDatabase()


    fun provideAuthenticationApi(): AuthenticationApi
    {
        return retrofit.create(AuthenticationApi::class.java)
    }

    fun provideRemoteRepository(): RemoteAuthRepository
    {
//        if(BuildConfig.DEBUG)
//        {
//            return MockAuthRepository()
//        }
        return DefaultRemoteAuthRepository()
    }

    fun provideTokenDao(): UserDao
    {
        return database.userDao()
    }

    fun provideLocalRepository(dao: UserDao): LocalAuthRepository
    {
        return DefaultLocalAuthRepository(dao)
    }

}