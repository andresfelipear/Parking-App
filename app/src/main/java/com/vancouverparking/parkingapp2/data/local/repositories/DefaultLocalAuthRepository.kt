package com.vancouverparking.parkingapp2.data.local.repositories

class DefaultLocalAuthRepository: LocalAuthRepository
{
    override suspend fun login(email: String,
                               password: String): String?
    {
        TODO("Not yet implemented")
    }

    override suspend fun singUp(email: String,
                                password: String): String?
    {
        TODO("Not yet implemented")
    }
}