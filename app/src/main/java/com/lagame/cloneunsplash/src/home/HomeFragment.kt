package com.lagame.cloneunsplash.src.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lagame.cloneunsplash.config.ApplicationClass
import com.lagame.cloneunsplash.config.ApplicationClass.Companion.bookmarks
import com.lagame.cloneunsplash.config.ApplicationClass.Companion.sSharedPreferences
import com.lagame.cloneunsplash.databinding.FragmentHomeBinding
import com.lagame.cloneunsplash.src.home.bookmark.BookMarkDTO
import com.lagame.cloneunsplash.src.home.bookmark.BookmarkRcvAdapter
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import com.lagame.cloneunsplash.src.home.photos.PhotosRcvAdapter
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), HomeFragmentInterface {

    lateinit var binding: FragmentHomeBinding
    private lateinit var photoLayoutManager: StaggeredGridLayoutManager

    // photo variables
    private var photos = ArrayList<HomePhotosDTO>()
    private var photoLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkConfig()

        // photos 불러오기
        HomeService(this).tryGetPhotos()
        //photoInfiniteScroll()
    }

    private fun bookmarkConfig() {

        photoLayoutManager =
            StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        binding.homeRcvBookmark.layoutManager = photoLayoutManager

        val bookMarkJson : String? = sSharedPreferences.getString("bookmark", null)
        if(bookMarkJson != null && bookMarkJson != "[]") {
            bookmarks = GsonBuilder().create().fromJson(
                bookMarkJson, object : TypeToken<ArrayList<BookMarkDTO>>() {}.type
            )
            Log.d("Test","res: ${bookmarks.toString()}")
            val bookmarkRcvAdapter = BookmarkRcvAdapter(bookmarks)
            binding.homeRcvBookmark.adapter = bookmarkRcvAdapter

            // go to detail
            bookmarkRcvAdapter.setBookmarkClickListener(object : BookmarkRcvAdapter.BookmarkClickListener{
                override fun onBookmarkClick(url: String?, id: String?) {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url, id)
                    Navigation.findNavController(requireView()).navigate(action)
                }
            })


            binding.homeBookmark.visibility = View.VISIBLE
            binding.homeTvBookmark.visibility = View.VISIBLE
        }else{
            binding.homeBookmark.visibility = View.GONE
            binding.homeTvBookmark.visibility = View.GONE
        }
    }

    // rcv 데이터 불러오기
    private fun photosConfig(photos: List<HomePhotosDTO>) {

        binding.homeRcvPhoto.setHasFixedSize(true)
        photoLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.homeRcvPhoto.layoutManager = photoLayoutManager

        val photosRcvAdapter = PhotosRcvAdapter(photos)
        binding.homeRcvPhoto.adapter = photosRcvAdapter
        
        // 버튼 클릭 이벤트 설정
        photosRcvAdapter.setPhotoClickListener(object : PhotosRcvAdapter.PhotoClickListener{
            override fun onPhotoClick(url: String, id: String) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(url, id)
                Navigation.findNavController(requireView()).navigate(action)
            }
        })

    }

    // 무한 스크롤 처리
    private fun photoInfiniteScroll() {
        binding.homeRcvPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(isLastItemDisplaying(binding.homeRcvPhoto)){
                    Log.d("Test", "스크롤 마지막")
                    binding.homeRcvLoading.rcvLoading.visibility = View.VISIBLE
                    HomeService(this@HomeFragment).tryGetPhotos()
                    photoLoading = true
                }
            }
        })
    }

    //return true if it's last item visited
    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {
            //int lastVisibleItemPosition = ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPositions();
            val lastVisibleItemPositions =
                (Objects.requireNonNull(recyclerView.layoutManager) as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                    null)
            // get maximum element within the list
            val lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!
                .itemCount - 1
        }
        return false
    }


    //get last item
    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Test", "프레그먼트 종료")
    }

    // photos 가져오기
    override fun onGetPhotosSuccess(response: List<HomePhotosDTO>) {
        Log.d("Retrofit", "${response[0]}")

        if(photoLoading){
            binding.homeRcvPhoto.adapter!!.notifyItemInserted(photos.size)
            photoLoading = false
        }
        binding.homeRcvLoading.rcvLoading.visibility = View.GONE
        photos.addAll(response)
        photosConfig(photos)

    }

    override fun onGetPhotosFailure(message: String) {
        Log.d("Retrofit", "$message")
    }

}