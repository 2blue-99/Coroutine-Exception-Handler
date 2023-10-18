package com.example.basepractice.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basepractice.databinding.ItemBinding

/**
 * 2023-03-22
 * pureum
 */

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private lateinit var cardBinding: ItemBinding
    lateinit var onClickData: (String) -> Unit

    var dataList = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MyHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.data = item
//            if(positionList.contains(position))
//                binding.listLayout.setBackgroundColor(Color.GRAY)

            binding.listLayout.setOnClickListener{
                onClickData(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        cardBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(cardBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int = dataList.size
}