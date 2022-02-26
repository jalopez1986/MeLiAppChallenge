package com.example.meliapp.app.search.list.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meliapp.R
import com.example.meliapp.core.search.domain.Item
import java.text.NumberFormat

class ItemsViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    val rootCardView: CardView = view.findViewById(R.id.item_root_cardview)
    private val image: ImageView = view.findViewById(R.id.item_result_image)
    private val title: TextView = view.findViewById(R.id.item_result_title)
    private val price: TextView = view.findViewById(R.id.item_result_price)
    private val shipping: TextView = view.findViewById(R.id.item_result_shipping)

    fun bind(item: Item) {
        title.text = item.title
        price.text = resolvePriceText(item.price)
        shipping.text = resolveShipmentText(item.shipping.free_shipping)

        Glide.with(itemView)
            .load(item.thumbnail)
            .centerCrop()
            .into(image)
    }

    private fun resolvePriceText(price: Double): String {
        val format = NumberFormat.getCurrencyInstance()
        return format.format(price)
    }

    private fun resolveShipmentText(freeshipping: Boolean): String {
        return if (freeshipping) view.context.getString(R.string.item_result_freeshipping) else ""
    }
}