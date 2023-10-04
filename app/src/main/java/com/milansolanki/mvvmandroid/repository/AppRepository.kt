package com.milansolanki.mvvmandroid.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.models.Photo
import com.milansolanki.mvvmandroid.models.Result
import com.milansolanki.mvvmandroid.models.User
import com.milansolanki.mvvmandroid.paging.UnsplashDataSource
import com.milansolanki.mvvmandroid.retrofit.ApiService
import com.milansolanki.mvvmandroid.room.UserDao
import com.milansolanki.mvvmandroid.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) {

    fun getPhotos(query: String): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(pageSize = 20)
        ) {
            UnsplashDataSource(apiService, query)
        }.flow
    }

    suspend fun registerUser(user: User): Result<Long> {
        return try {
            val existingUser = userDao.getUser(userName = user.email)
            if (existingUser != null) {
                Result.Error(messageResourceId = R.string.msg_email_already_used)
            } else {
                userPreferences.saveUserInfo(user)
                Result.Success(userDao.insert(user))
            }
        } catch (e: Exception) {
            Result.Error(messageResourceId = R.string.msg_database_exception)
        }
    }

    suspend fun loginUser(user: User): Result<User> {
        return try {
            val result = userDao.getUser(userName = user.email, pass = user.password)
            if (result == null) {
                Result.Error(messageResourceId = R.string.msg_invalid_credentials)
            } else {
                userPreferences.saveUserInfo(result)
                Result.Success(result)
            }
        } catch (e: Exception) {
            Result.Error(messageResourceId = R.string.msg_database_exception)
        }
    }

    fun logoutUser(): Result<Unit> {
        return try {
            userPreferences.saveUserInfo(null)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(messageResourceId = R.string.msg_shared_preferences_exception)
        }
    }

    fun loggedInUserInfo(): Result<User?> {
        return try {
            Result.Success(userPreferences.getUserInfo())
        } catch (e: Exception) {
            Result.Error(messageResourceId = R.string.msg_shared_preferences_exception)
        }
    }
}
