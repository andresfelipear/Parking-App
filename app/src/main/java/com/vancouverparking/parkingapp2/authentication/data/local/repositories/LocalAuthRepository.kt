package com.vancouverparking.parkingapp2.authentication.data.local.repositories

import com.vancouverparking.parkingapp2.authentication.data.local.entities.UserDetails

interface LocalAuthRepository
{
    suspend fun getUsers(): List<UserDetails>

    suspend fun getUserByTokenId(token: String) : UserDetails?
}