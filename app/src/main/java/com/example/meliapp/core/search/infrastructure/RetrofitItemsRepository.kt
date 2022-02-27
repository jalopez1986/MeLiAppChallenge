package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.Description
import com.example.meliapp.core.search.domain.Item
import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.core.search.domain.exceptions.ApiResponseException
import com.example.meliapp.core.search.domain.exceptions.GenericNetworkException
import java.io.IOException
import javax.inject.Inject

class RetrofitItemsRepository @Inject constructor(
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

            return Result.failure(ApiResponseException("Error getting Item ${response.code()} ${response.message()}"))
        } catch (exception: Exception) {
            return Result.failure(GenericNetworkException("something wrong"))
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