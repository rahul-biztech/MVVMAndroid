package com.milansolanki.mvvmandroid.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.milansolanki.mvvmandroid.BuildConfig
import com.milansolanki.mvvmandroid.models.Photo
import com.milansolanki.mvvmandroid.retrofit.ApiService

class UnsplashDataSource(private val apiService: ApiService, private val searchQuery: String) :
    PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getPhotos(
                BuildConfig.UNSPLASH_CLIENT_ID,
                position,
                params.loadSize,
                searchQuery
            )
            val photoList =
                Gson().fromJson<List<Photo>>(
                    response.body()!!.get("results").asJsonArray,
                    object : TypeToken<List<Photo>>() {}.type
                )
            LoadResult.Page(
                data = photoList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.body() == null || photoList.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}