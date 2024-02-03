package com.lagame.cloneunsplash.src.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lagame.cloneunsplash.databinding.FragmentHomeBinding
import com.lagame.cloneunsplash.src.home.bookmark.BookmarkItemsData
import com.lagame.cloneunsplash.src.home.bookmark.BookmarkRcvAdapter
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import com.lagame.cloneunsplash.src.home.photos.PhotosRcvAdapter
import com.lagame.cloneunsplash.util.GridSpacingItemDecoration
import com.lagame.cloneunsplash.util.fromDpToPx

class HomeFragment : Fragment(), HomeFragmentInterface {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkConfig()

        // photos 불러오기
        HomeService(this).tryGetPhotos()

    }

    private fun bookmarkConfig(){

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        binding.homeRcvBookmark.layoutManager = staggeredGridLayoutManager

        var itemsData = ArrayList<BookmarkItemsData>()
        itemsData.add(BookmarkItemsData("_url", 150, 120))
        itemsData.add(BookmarkItemsData("_url", 150, 120))
        itemsData.add(BookmarkItemsData("_url", 150, 120))
        itemsData.add(BookmarkItemsData("_url", 150, 120))

        val bookmarkRcvAdapter = BookmarkRcvAdapter(itemsData)
        binding.homeRcvBookmark.adapter = bookmarkRcvAdapter
    }
    
    // rcv 데이터 불러오기
    private fun photosConfig(photos: List<HomePhotosDTO>){

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.homeRcvPhoto.layoutManager = staggeredGridLayoutManager

        val photosRcvAdapter = PhotosRcvAdapter(photos)
        binding.homeRcvPhoto.adapter = photosRcvAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    // photos 가져오기
    override fun onGetPhotosSuccess(response: List<HomePhotosDTO>) {
        Log.d("Retrofit","${response[0]}")
        photosConfig(response)
    }

    override fun onGetPhotosFailure(message: String) {
        Log.d("Retrofit","$message")
    }


}