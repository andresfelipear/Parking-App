package com.vancouverparking.parkingapp2.authentication.data.remote.repositories

class DefaultRemoteAuthRepository: RemoteAuthRepository
{
    override suspend fun login(email: String,
                               password: String): String?
    {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(email: String,
                                password: String): String?
    {
        TODO("Not yet implemented")
    }
}