package com.vancouverparking.parkingapp2.authentication.data.local.repositories

import com.vancouverparking.parkingapp2.authentication.data.local.daos.UserDao
import com.vancouverparking.parkingapp2.authentication.data.local.entities.UserDetails

class DefaultLocalAuthRepository(
    private val dao: UserDao
): LocalAuthRepository
{
    override suspend fun getUsers(): List<UserDetails>
    {
        return dao.getUsers()
    }

    override suspend fun getUserByTokenId(token: String): UserDetails?
    {
        return dao.getUserByTokenId(token)
    }

}