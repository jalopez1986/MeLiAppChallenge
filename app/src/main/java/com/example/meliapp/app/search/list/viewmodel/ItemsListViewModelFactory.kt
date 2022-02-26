package com.example.meliapp.app.search.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meliapp.core.search.actions.SearchItemsByQuery
import javax.inject.Inject

class ItemsListViewModelFactory @Inject constructor(
    private val searchItemsByQuery: SearchItemsByQuery
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsListViewModel(
            searchItemsByQuery
        ) as T
    }
}