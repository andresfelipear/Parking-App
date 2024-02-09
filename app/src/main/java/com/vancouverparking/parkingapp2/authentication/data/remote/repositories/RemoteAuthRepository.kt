package com.vancouverparking.parkingapp2.authentication.data.remote.repositories

interface RemoteAuthRepository
{
    suspend fun login(email: String, password: String): String?
    suspend fun signUp(email: String, password: String): String?
    suspend fun forgotPassword(email: String, mobile: String): String?
    suspend fun resetPassword(email: String, password: String): String?
}