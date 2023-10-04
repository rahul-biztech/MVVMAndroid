package com.milansolanki.mvvmandroid.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.milansolanki.mvvmandroid.models.Photo
import com.milansolanki.mvvmandroid.repository.AppRepository
import com.milansolanki.mvvmandroid.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: AppRepository
) :
    BaseViewModel() {

    private val searchQuery = MutableStateFlow("Nature")

    @OptIn(ExperimentalCoroutinesApi::class)
    val loadPhotos: Flow<PagingData<Photo>> = searchQuery.flatMapLatest { searchQuery ->
        repository.getPhotos(searchQuery)
            .cachedIn(viewModelScope)
    }

    fun searchQuery(query: String) {
        searchQuery.value = query
    }
}