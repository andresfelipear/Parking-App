package com.vancouverparking.parkingapp2.authentication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserDetailsEntity")
data class UserDetails(
    @PrimaryKey val token: String,
    val email:String,
    val phoneNumber: Int,
    val fullName:String,
)
