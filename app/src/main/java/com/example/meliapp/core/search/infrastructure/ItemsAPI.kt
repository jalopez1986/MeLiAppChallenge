package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.Description
import com.example.meliapp.core.search.domain.Item
import com.example.meliapp.core.search.domain.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemsAPI {
    @GET("sites/MLA/search?")
    suspend fun searchByQuery(@Query("q") query: String): Response<ItemsResponse>

    @GET("items/{id}")
    suspend fun searchItemDetail(@Path("id") itemId: String): Response<Item>

    @GET("items/{id}/description")
    suspend fun searchItemDescription(@Path("id") itemId: String): Response<Description>
}
