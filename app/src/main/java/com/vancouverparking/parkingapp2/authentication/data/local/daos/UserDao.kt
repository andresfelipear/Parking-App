package com.vancouverparking.parkingapp2.authentication.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vancouverparking.parkingapp2.authentication.data.local.entities.UserDetails

@Dao
interface UserDao
{
    @Upsert
    suspend fun insertUser(userDetails: UserDetails)

    @Query("SELECT * FROM UserDetailsEntity")
    suspend fun getUsers(): List<UserDetails>

    @Query("SELECT * FROM UserDetailsEntity WHERE token = :userToken")
    suspend fun getUserByTokenId(userToken: String): UserDetails?

}