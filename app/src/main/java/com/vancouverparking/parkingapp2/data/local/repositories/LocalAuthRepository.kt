package com.vancouverparking.parkingapp2.data.local.repositories

interface LocalAuthRepository
{
    suspend fun login(email: String, password: String): String?
    suspend fun singUp(email: String, password: String): String?
}