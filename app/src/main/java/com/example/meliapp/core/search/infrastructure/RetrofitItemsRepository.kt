package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.Description
import com.example.meliapp.core.search.domain.Item
import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import javax.inject.Inject

class RetrofitItemsRepository @Inject constructor(
    private val api: ItemsAPI
) : ItemsRepository {
    override suspend fun searchByQuery(query: String, offset: Int): Result<ItemsResponse> {
        try {
            val response = api.searchByQuery(query, offset)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }

            return Result.failure(RuntimeException("Error getting Item ${response.code()} ${response.message()}"))
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }

    override suspend fun searchItemDetail(id: String): Result<Item> {
        try {
            val response = api.searchItemDetail(id)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }

            return Result.failure(RuntimeException("Error getting Item ${response.code()} ${response.message()}"))
        } catch (exception: Exception) {
            val a = exception.localizedMessage
            return Result.failure(exception)
        }
    }

    override suspend fun searchItemDescription(id: String): Result<Description> {
        try {
            val response = api.searchItemDescription(id)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    return Result.success(body)
                }
            }

            return Result.failure(RuntimeException("Error getting Item ${response.code()} ${response.message()}"))
        } catch (exception: Exception) {
            return Result.failure(exception)
        }
    }
}