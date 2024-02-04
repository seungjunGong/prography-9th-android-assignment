package com.lagame.cloneunsplash.src.random

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.config.ApplicationClass
import com.lagame.cloneunsplash.databinding.HomeRcvPhotosItemBinding
import com.lagame.cloneunsplash.databinding.RandomPhotoRcvItemBinding
import com.lagame.cloneunsplash.src.home.bookmark.BookMarkDTO
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import com.lagame.cloneunsplash.src.home.photos.PhotosRcvAdapter

class RandomPhotoVpAdapter(private val itemsData: ArrayList<HomePhotosDTO>) :
    RecyclerView.Adapter<RandomPhotoVpAdapter.PagerViewHolder>() {

    interface InfoClickListener {
        fun onInfoClick(url: String, id: String)
    }
    private lateinit var fInfoClickListener: InfoClickListener
    fun setInfoClickListener(infoClickListener: InfoClickListener){
        fInfoClickListener = infoClickListener
    }


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

            bookmarkConfig(data)

            binding.randomPhotoIvInfo.setOnClickListener {
                fInfoClickListener.onInfoClick(url = data.urls.raw, id = data.id)
            }
        }

        private fun bookmarkConfig(data: HomePhotosDTO) {
            binding.randomPhotoRcvIv.setOnClickListener {
                val idToRemoveOrAdd = data.id ?: return@setOnClickListener  // args.id가 null이면 함수 종료
                val editor: SharedPreferences.Editor = ApplicationClass.sSharedPreferences.edit()

                if (ApplicationClass.bookmarks.any { it.id == idToRemoveOrAdd }) {
                    ApplicationClass.bookmarks.removeAll { it.id == idToRemoveOrAdd }
                    Log.d("Test", "${ApplicationClass.bookmarks.toString()}")
                } else {
                    ApplicationClass.bookmarks.add(BookMarkDTO(data.urls.raw, idToRemoveOrAdd))
                    // 해당 북마크 추가 로직 추가
                }
                val jsonData = GsonBuilder().create().toJson(
                    ApplicationClass.bookmarks,
                    object : TypeToken<ArrayList<BookMarkDTO>>() {}.type
                )
                editor.putString("bookmark", jsonData) // SharedPreferences에 push
                editor.apply() // SharedPreferences 적용
            }
        }

        private fun showDetail(data: HomePhotosDTO){

        }
    }

}