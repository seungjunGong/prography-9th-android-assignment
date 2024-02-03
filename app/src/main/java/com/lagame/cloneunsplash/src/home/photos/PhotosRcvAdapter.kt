package com.lagame.cloneunsplash.src.home.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.HomeRcvPhotosItemBinding

class PhotosRcvAdapter (private val itemsData: List<HomePhotosDTO>): RecyclerView.Adapter<PhotosRcvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomeRcvPhotosItemBinding = HomeRcvPhotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsData[position])
    }

    override fun getItemCount(): Int = itemsData.size

    inner class ViewHolder(private val binding: HomeRcvPhotosItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: HomePhotosDTO){
            Glide
                .with(binding.homeRcvPhotoIv.context)
                .load(data.urls.raw)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.homeRcvPhotoIv)
        }
    }


}