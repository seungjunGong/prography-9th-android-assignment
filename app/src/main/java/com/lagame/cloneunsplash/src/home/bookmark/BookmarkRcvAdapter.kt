package com.lagame.cloneunsplash.src.home.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.HomeRcvBookmarkItemBinding

class BookmarkRcvAdapter(private val itemsData: ArrayList<BookMarkDTO>): RecyclerView.Adapter<BookmarkRcvAdapter.ViewHolder>() {

    interface BookmarkClickListener {
        fun onBookmarkClick(url: String?, id: String?)
    }
    private lateinit var fBookmarkClickListener: BookmarkRcvAdapter.BookmarkClickListener
    fun setBookmarkClickListener(bookmarkClickListener: BookmarkRcvAdapter.BookmarkClickListener){
        fBookmarkClickListener = bookmarkClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomeRcvBookmarkItemBinding = HomeRcvBookmarkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsData[position])
    }

    override fun getItemCount(): Int = itemsData.size

    inner class ViewHolder(private val binding: HomeRcvBookmarkItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: BookMarkDTO){
            Glide
                .with(binding.homeRcvBookmarkIv.context)
                .load(data.url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_background)
                .into(binding.homeRcvBookmarkIv)

            binding.homeRcvBookmarkIv.setOnClickListener {
                fBookmarkClickListener.onBookmarkClick(url = data.url, id = data.id)
            }
        }
    }

}