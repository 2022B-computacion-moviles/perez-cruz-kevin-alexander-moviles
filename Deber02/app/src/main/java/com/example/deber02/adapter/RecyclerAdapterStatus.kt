package com.example.deber02.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deber02.data.Status
import com.example.deber02.databinding.ItemStatusLayoutBinding

class RecyclerAdapterStatus(private val data : ArrayList<Status>) : RecyclerView.Adapter<RecyclerAdapterStatus.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemStatusLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(position: Int) {
            binding.itemProfileName.text = data[position].statusProfileName
            binding.itemStatusUploadDate.text = data[position].statusDate
            Glide
                .with(binding.root.context)
                .load(data[position].statusProfileImage)
                .into(binding.itemStatusImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatusLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}