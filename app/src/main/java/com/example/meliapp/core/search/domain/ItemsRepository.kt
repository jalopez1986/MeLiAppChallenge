package com.example.meliapp.core.search.domain

interface ItemsRepository {
    suspend fun searchByQuery(query: String): Result<ItemsResponse>
    suspend fun searchItemDetail(id: String): Result<Item>
    suspend fun searchItemDescription(id: String): Result<Description>

}
