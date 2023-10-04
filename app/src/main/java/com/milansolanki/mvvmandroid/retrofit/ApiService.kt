package com.milansolanki.mvvmandroid.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("query") searchKeyword: String,
    ): Response<JsonObject>

}