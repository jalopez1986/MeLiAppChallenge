package com.example.meliapp.app.search.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.core.search.actions.SearchItemsByQuery
import com.example.meliapp.core.search.domain.ItemsResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsListViewModel(
    private val searchItemsByQuery: SearchItemsByQuery
) : ViewModel() {

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    private val _items = MutableLiveData<Result<ItemsResponse>>()
    val items: LiveData<Result<ItemsResponse>>
        get() = _items


    fun searchItemsByQuery(query: String) {
        viewModelScope.launch {
            _loader.postValue(true)
            searchItemsByQuery.execute(query).collect {
                _loader.postValue(false)
                _items.postValue(it)
            }
        }
    }
}