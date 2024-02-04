package com.lagame.cloneunsplash.src.home.photos

import android.app.Activity
import android.app.Application
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.HomeRcvPhotosItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.coroutines.coroutineContext

class PhotosRcvAdapter (private val itemsData: List<HomePhotosDTO>): RecyclerView.Adapter<PhotosRcvAdapter.ViewHolder>() {

    interface PhotoClickListener {
        fun onPhotoClick(url: String, id: String)
    }
    private lateinit var fPhotoClickListener: PhotoClickListener
    fun setPhotoClickListener(photoClickListener: PhotoClickListener){
        fPhotoClickListener = photoClickListener
    }

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
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_background)
                .into(binding.homeRcvPhotoIv)

            binding.homeRcvPhotoIv.setOnClickListener {
                fPhotoClickListener.onPhotoClick(url = data.urls.raw, id = data.id)
            }
        }
    }

}