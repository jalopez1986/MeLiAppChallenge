package com.example.meliapp.core.search.actions

import com.example.meliapp.core.search.domain.ItemInfo
import com.example.meliapp.core.search.domain.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchItemDetail @Inject constructor(
    private val itemsRepository: ItemsRepository
) {
    suspend fun execute(id: String): Flow<Result<ItemInfo>> {
        val itemDetail = itemsRepository.searchItemDetail(id)
        val description = itemsRepository.searchItemDescription(id)

        if (itemDetail.isSuccess && description.isSuccess) {
            return flow {
                emit(Result.success(ItemInfo(itemDetail.getOrNull()!!, description.getOrNull()!!)))
            }
        } else {
            return flow {
                emit(Result.failure(RuntimeException("Error obteniendo el detalle")))
            }
        }
    }
}