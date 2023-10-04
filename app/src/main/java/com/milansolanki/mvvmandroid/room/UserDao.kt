package com.milansolanki.mvvmandroid.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.milansolanki.mvvmandroid.models.User

@Dao
interface UserDao {

    @Query(
        "SELECT * FROM user WHERE email = :userName AND password =:pass"
    )
    suspend fun getUser(userName: String?, pass: String?): User?

    @Query(
        "SELECT * FROM user WHERE email = :userName"
    )
    suspend fun getUser(userName: String?): User?

    @Insert
    suspend fun insert(user: User): Long

}