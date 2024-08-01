package com.vancouverparking.parkingapp2.authentication.di

import android.content.Context
import com.vancouverparking.parkingapp2.App
import com.vancouverparking.parkingapp2.MainActivity
import com.vancouverparking.parkingapp2.authentication.data.local.daos.UserDao
import com.vancouverparking.parkingapp2.authentication.data.local.database.ParkingDatabase

object DatabaseModule
{
    fun provideParkingAppDatabase(): ParkingDatabase
    {
        return ParkingDatabase.buildDatabase(context = App.APP_CONTEXT)
    }

}