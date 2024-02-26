package com.vancouverparking.parkingapp2.authentication.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vancouverparking.parkingapp2.authentication.data.local.daos.UserDao
import com.vancouverparking.parkingapp2.authentication.data.local.entities.UserDetails

@Database(entities = [UserDetails::class], version = 1, exportSchema = false)
abstract class ParkingDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDao

    companion object
    {
        private lateinit var database: ParkingDatabase

        fun buildDatabase(context: Context): ParkingDatabase
        {
            if(!this::database.isInitialized)
            {
                synchronized(this)
                {
                    database = Room.databaseBuilder(
                        context,
                        ParkingDatabase::class.java, context.packageName.toString()
                    ).build()
                }
            }
            return database
        }
    }

}