package com.lagame.cloneunsplash.src.random

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.FragmentHomeBinding
import com.lagame.cloneunsplash.databinding.FragmentRandomPhotoBinding
import com.lagame.cloneunsplash.src.home.HomeFragmentDirections
import com.lagame.cloneunsplash.src.home.photos.HomePhotosDTO
import com.lagame.cloneunsplash.src.home.photos.PhotosRcvAdapter

class RandomPhotoFragment : Fragment(), RandomPhotoFragmentInterface {

    lateinit var binding: FragmentRandomPhotoBinding

    // photo variables
    private var randomPhotos = ArrayList<HomePhotosDTO>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // random photo 불러오기
        RandomPhotoService(this).tryGetRandomPhotos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun randomPhotosConfig(photos: ArrayList<HomePhotosDTO>) {

        binding.randomPhotoVp.apply {
            adapter = RandomPhotoVpAdapter(photos)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    override fun onGetRandomPhotosSuccess(response: HomePhotosDTO) {
        Log.d("Retrofit", "$response")
        randomPhotos.add(response)
        if(randomPhotos.size < 5)
            RandomPhotoService(this).tryGetRandomPhotos()
        else
            randomPhotosConfig(randomPhotos)
    }

    override fun onGetRandomPhotosFailure(message: String) {
        Log.d("Retrofit", "$message")
    }


}