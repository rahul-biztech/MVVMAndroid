package com.milansolanki.mvvmandroid.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.milansolanki.mvvmandroid.models.Photo
import com.milansolanki.mvvmandroid.ui.composables.AppScaffold
import com.milansolanki.mvvmandroid.ui.composables.AppSearchBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var isSearchActive by remember {
        mutableStateOf(false)
    }
    val photoList = viewModel.loadPhotos.collectAsLazyPagingItems()

    AppScaffold(
        viewModel = viewModel,
        navController = navController, horizontalPadding = if (isSearchActive) 0 else 12,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (!isSearchActive)
                CenterAlignedTopAppBar(
                    title = { Text("MVVM Android") },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(scrolledContainerColor = Color.White)
                )
        }
    ) {
        Column {
            AppSearchBar(
                navController = navController,
                onSearch = { viewModel::searchQuery.invoke(it) },
                onActiveChange = {
                    isSearchActive = it
                })
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                when (photoList.loadState.refresh) {
                    is LoadState.Error -> Text((photoList.loadState.refresh as LoadState.Error).error.message.toString())
                    is LoadState.Loading -> CircularProgressIndicator()
                    is LoadState.NotLoading -> LazyColumn {
                        items(photoList.itemCount, key = { photoList[it]?.urls!!.small }) {
                            Row(Modifier.animateItemPlacement()) {
                                PhotoItem(photo = photoList[it])
                            }
                        }

                        when (photoList.loadState.append) {
                            is LoadState.Error -> {
                                item { Text((photoList.loadState.append as LoadState.Error).error.message.toString()) }
                            }

                            is LoadState.Loading -> item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is LoadState.NotLoading -> Unit
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(
                color = android.graphics.Color.parseColor(
                    photo?.color
                )
            )
        ),
        modifier = Modifier.padding(bottom = 12.dp),
    ) {
        Box {
            AsyncImage(
                model = photo?.urls?.small,
                contentScale = ContentScale.Crop,
                contentDescription = photo?.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            photo?.description?.let {
                Text(
                    it,
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(all = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }
        }
    }


}


