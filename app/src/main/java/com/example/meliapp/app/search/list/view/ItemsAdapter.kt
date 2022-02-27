package com.example.meliapp.app.search.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.core.search.domain.Item

class ItemsAdapter(
    private val data: List<Item>,
    private val itemClickListener: (Item) -> Unit,
) : RecyclerView.Adapter<ItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

        holder.rootCardView.setOnClickListener { itemClickListener(item) }
    }
}