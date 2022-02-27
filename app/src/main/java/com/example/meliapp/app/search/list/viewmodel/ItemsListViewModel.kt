package com.example.meliapp.app.search.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.core.search.actions.SearchItemsByQuery
import com.example.meliapp.core.search.domain.Item
import com.example.meliapp.core.search.domain.ItemsResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsListViewModel(
    private val searchItemsByQuery: SearchItemsByQuery
) : ViewModel() {

    private val totalItems = mutableListOf<Item>()
    private var totalItemsCount = Int.MAX_VALUE

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    private val _items = MutableLiveData<Result<ItemsResponse>>()
    val items: LiveData<Result<ItemsResponse>>
        get() = _items

    private lateinit var query: String


    fun searchItemsByQuery(_query: String) {
        query = _query
        searchItemsByQuery(query, 0)
    }

    fun loadMoreItems(position: Int) {
        searchItemsByQuery(query, position)
    }

    private fun searchItemsByQuery(_query: String, offset: Int) {
        query = _query
        if (offset >= totalItemsCount ) { return }

        viewModelScope.launch {
            _loader.postValue(true)
            searchItemsByQuery.execute(query, offset).collect {
                if (it.isSuccess && it.getOrNull()!= null) {
                    totalItems.addAll(it.getOrNull()!!.results)
                    it.getOrNull()!!.results = totalItems
                    totalItemsCount = it.getOrNull()!!.paging.total
                }
                _loader.postValue(false)
                _items.postValue(it)
            }
        }
    }
}