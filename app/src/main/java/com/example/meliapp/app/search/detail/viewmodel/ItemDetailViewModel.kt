package com.example.meliapp.app.search.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meliapp.core.search.actions.SearchItemDetail
import com.example.meliapp.core.search.domain.ItemInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val searchItemDetail: SearchItemDetail
) : ViewModel() {

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    private val _itemDetail = MutableLiveData<Result<ItemInfo>>()
    val itemDetail: LiveData<Result<ItemInfo>>
        get() = _itemDetail


    fun searchItemDescription(id: String) {
        viewModelScope.launch {
            _loader.postValue(true)
            searchItemDetail.execute(id).collect {
                if (it.isSuccess) {
                    _loader.postValue(false)
                    _itemDetail.postValue(it)
                }
            }
        }

    }
}