package com.example.meliapp.core.search.domain

interface ItemsRepository {
    suspend fun searchByQuery(query: String): Result<ItemsResponse>

}
