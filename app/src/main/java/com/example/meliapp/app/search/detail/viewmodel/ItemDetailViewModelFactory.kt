package com.example.meliapp.app.search.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meliapp.core.search.actions.SearchItemDetail
import javax.inject.Inject

class ItemDetailViewModelFactory @Inject constructor(
    private val searchItemDetail: SearchItemDetail
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemDetailViewModel(
            searchItemDetail
        ) as T
    }
}