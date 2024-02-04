package com.lagame.cloneunsplash.src.home.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lagame.cloneunsplash.databinding.HomeRcvBookmarkItemBinding

class BookmarkRcvAdapter(private val itemsData: ArrayList<BookmarkItemsData>): RecyclerView.Adapter<BookmarkRcvAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomeRcvBookmarkItemBinding = HomeRcvBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsData[position])
    }

    override fun getItemCount(): Int = itemsData.size

    inner class ViewHolder(private val binding: HomeRcvBookmarkItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: BookmarkItemsData){

        }
    }

}