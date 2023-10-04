package com.milansolanki.mvvmandroid.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.milansolanki.mvvmandroid.R
import com.milansolanki.mvvmandroid.navigation.NavRoute
import com.milansolanki.mvvmandroid.utils.isValidString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    navController: NavHostController,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    var active by remember {
        mutableStateOf(false)
    }

    val searchHistory = remember { mutableStateListOf("") }

    SearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            onSearch.invoke(it)
            searchHistory.add(it)
            active = false
            onActiveChange.invoke(false)
        },
        active = active,
        shape = SearchBarDefaults.fullScreenShape,
        placeholder = { Text(stringResource(id = R.string.lbl_search)) },
        leadingIcon = { Icon(Icons.Default.Search, stringResource(id = R.string.lbl_search)) },
        trailingIcon = {
            if (active) AppIcon(
                icon = Icons.Default.Close,
                contentDescription = R.string.lbl_close,
                onClick = {
                    active = false
                    text = ""
                    onActiveChange.invoke(false)

                }) else {
                AppIcon(
                    modifier = Modifier.size(38.dp),
                    icon = Icons.Default.AccountCircle,
                    contentDescription = R.string.lbl_profile,
                    onClick = {
                        navController.navigate(NavRoute.ProfileScreen.route)
                    })
            }
        },
        onActiveChange = {
            active = it
            onActiveChange.invoke(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(if (active) 0.dp else 12.dp))
    ) {
        searchHistory.forEach { item ->
            if (item.isValidString()) Row(modifier = Modifier.run { padding(all = 12.dp) }) {
                Icon(Icons.Default.Refresh, stringResource(R.string.lbl_refresh))
                Spacer(modifier = Modifier.width(12.dp))
                Text(item)
            }
        }
    }
}
