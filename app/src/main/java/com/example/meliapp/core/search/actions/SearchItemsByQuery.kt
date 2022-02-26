package com.example.meliapp.core.search.actions

import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchItemsByQuery(
    private val itemsRepository: ItemsRepository
) {
    suspend fun execute(query: String): Flow<Result<ItemsResponse>> {
        val items = itemsRepository.searchByQuery(query)
        return flow {
            emit(items)
        }
    }
}