package com.example.meliapp.core.search.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ItemsResponse(
    @SerializedName("query") val query: String,
    @SerializedName("paging") val paging: Page,
    @SerializedName("results") val results: List<Item>,
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
    @SerializedName("sold_quantity") val sold: Int,
    @SerializedName("available_quantity") val available: Int,
)

data class Shipping(
    val free_shipping: Boolean,
    val logistic_type: String,
)

// data class para mostrar el detalle del item
data class ItemInfo(
    val item: Item,
    val description: Description,
)

data class Description(
    val plain_text: String,
)

data class Pictures(
    val url: String,
)

