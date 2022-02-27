package com.example.meliapp.app.search.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.core.search.domain.Item

class ItemsAdapter(
    private val onItemResultRecyclerListener: OnItemResultRecyclerListener
) : RecyclerView.Adapter<ItemsViewHolder>() {
    private var items: List<Item> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ItemsViewHolder(view, onItemResultRecyclerListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        // Invokes callback when the end of RecyclerView is reached

        // Invokes callback when the end of RecyclerView is reached
        if (position == items.size - 1) {
            onItemResultRecyclerListener.onBottomReached(items.size)
        }

        val item = items[position]
        holder.bind(item)
    }

    fun getResult(position: Int): Item? {
        return items[position]
    }

    fun setResults(results: List<Item>) {
        items = results
        notifyDataSetChanged()
    }

    interface OnItemResultRecyclerListener {
        fun onBottomReached(position: Int)
        fun onItemClicked(position: Int)
    }
}