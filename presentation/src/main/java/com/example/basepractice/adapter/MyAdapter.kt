package com.example.basepractice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basepractice.databinding.ItemBinding
import com.example.domain.model.MyData

/**
 * 2023-03-22
 * pureum
 */

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private lateinit var cardBinding: ItemBinding
    var dataList = mutableListOf<MyData>()
        set(value) {
            field = value.reversed().toMutableList()
            Log.e("TAG", "@@@@@@@@@@@@@ $field: ", )
            notifyDataSetChanged()
        }

    inner class MyHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyData) {
            binding.data = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        cardBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(cardBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}