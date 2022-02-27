package com.example.meliapp.core.search.domain

data class ItemsResponse(
    val query: String,
    val paging: Page,
    val results: List<Item>,
)

data class Page(
    val total: Int,
    val limit: Int,
)

data class Item(
    val id: String?,
    val title: String,
    val price: Double,
    val condition: String,
    val thumbnail: String,
    val pictures: List<Pictures>,
    val shipping: Shipping,
)

data class Shipping(
    val free_shipping: Boolean,
    val logistic_type: String,
)

data class Description(
    val plain_text: String,
)

data class Pictures(
    val url: String,
)

data class ItemInfo(
    val item: Item,
    val description: Description
)