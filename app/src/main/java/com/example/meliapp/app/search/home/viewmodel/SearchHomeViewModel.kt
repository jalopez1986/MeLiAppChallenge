package com.example.meliapp.app.search.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meliapp.app.util.Event

class SearchHomeViewModel : ViewModel() {
    private val _isValidSearchQuery = MutableLiveData<Event<Boolean>>()
    val isValidSearchQuery: LiveData<Event<Boolean>>
        get() = _isValidSearchQuery

    fun tryToMakeSearch(searchQuery: String) {
        if (searchQuery.trim().isEmpty()) {
            _isValidSearchQuery.postValue(Event(false))
            return
        }

        _isValidSearchQuery.postValue(Event(true))
    }
}