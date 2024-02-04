package com.lagame.cloneunsplash.src.random

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.HomeRcvPhotosItemBinding
import com.lagame.cloneunsplash.databinding.RandomPhotoRcvItemBinding
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO

class RandomPhotoVpAdapter(private val itemsData: ArrayList<HomePhotosDTO>) :
    RecyclerView.Adapter<RandomPhotoVpAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding: RandomPhotoRcvItemBinding =
            RandomPhotoRcvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(itemsData[position])
    }

    override fun getItemCount(): Int = itemsData.size

    inner class PagerViewHolder(private val binding: RandomPhotoRcvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: HomePhotosDTO){
            Glide
                .with(binding.randomPhotoRcvIv.context)
                .load(data.urls.raw)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_background)
                .into(binding.randomPhotoRcvIv)
        }
    }

}