package com.milansolanki.mvvmandroid.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.milansolanki.mvvmandroid.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}