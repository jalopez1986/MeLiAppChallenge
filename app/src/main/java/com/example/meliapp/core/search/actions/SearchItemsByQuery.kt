package com.example.meliapp.core.search.actions

import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchItemsByQuery @Inject constructor(
    private val itemsRepository: ItemsRepository
) {
    suspend fun execute(query: String, offset: Int): Flow<Result<ItemsResponse>> {
        val items = itemsRepository.searchByQuery(query, offset)
        return flow {
            emit(items)
        }
    }
}