package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import java.io.IOException

class RetrofitItemsRepository(
    private val api: ItemsAPI
) : ItemsRepository {
    override suspend fun searchByQuery(query: String): Result<ItemsResponse> {
        try {
            val response = api.searchByQuery(query)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }
            return Result.failure(IOException("Error getting Item ${response.code()} ${response.message()}"))
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }
}