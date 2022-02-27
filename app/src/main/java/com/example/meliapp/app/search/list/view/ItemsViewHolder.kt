package com.example.meliapp.app.search.list.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meliapp.R
import com.example.meliapp.app.util.FormatterHelper
import com.example.meliapp.core.search.domain.Item

class ItemsViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    val rootCardView: CardView = view.findViewById(R.id.item_root_cardview)
    private val image: ImageView = view.findViewById(R.id.item_result_image)
    private val title: TextView = view.findViewById(R.id.item_result_title)
    private val price: TextView = view.findViewById(R.id.item_result_price)
    private val shipping: TextView = view.findViewById(R.id.item_result_shipping)
    private val condition: TextView = view.findViewById(R.id.item_result_condition)

    fun bind(item: Item) {
        title.text = item.title
        price.text = FormatterHelper.resolvePriceText(item.price)
        shipping.text = FormatterHelper.resolveShipmentText(item.shipping.free_shipping)
        condition.text = FormatterHelper.resolveConditionText(item.condition)

        Glide.with(itemView)
            .load(item.thumbnail)
            .centerCrop()
            .into(image)
    }
}