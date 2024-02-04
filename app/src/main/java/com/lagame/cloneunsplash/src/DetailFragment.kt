package com.lagame.cloneunsplash.src

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.config.ApplicationClass.Companion.bookmarks
import com.lagame.cloneunsplash.config.ApplicationClass.Companion.sSharedPreferences
import com.lagame.cloneunsplash.databinding.FragmentDetailBinding
import com.lagame.cloneunsplash.src.home.bookmark.BookMarkDTO

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        // bottom navi hide
        val mainAct = activity as MainActivity
        mainAct.HideBottomNavi(true)

        statusBarBlack90()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 이미지 불러오기
        Glide
            .with(binding.detailPhotoIv.context)
            .load(args.url)
            .placeholder(R.drawable.loading)
            .error(R.drawable.ic_launcher_background)
            .into(binding.detailPhotoIv)

        // cancel button 처리
        binding.detailIvCancel.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }

        // 북 마크 처리
        bookmarkConfig()
    }

    private fun bookmarkConfig(){
        if (bookmarks.any { it.id == args.id}) {
            binding.detailIvBookmark.setImageResource(R.drawable.ic_bookmark)
        }

        binding.detailIvBookmark.setOnClickListener {
            val idToRemoveOrAdd = args.id ?: return@setOnClickListener  // args.id가 null이면 함수 종료
            val editor: SharedPreferences.Editor = sSharedPreferences.edit()

            if (bookmarks.any { it.id == idToRemoveOrAdd }) {
                bookmarks.removeAll { it.id == idToRemoveOrAdd }
                Log.d("Test", "${bookmarks.toString()}")
                binding.detailIvBookmark.setImageResource(R.drawable.ic_bookmark_inactive)
            } else {
                bookmarks.add(BookMarkDTO(args.url, idToRemoveOrAdd))
                // 해당 북마크 추가 로직 추가
                // 북마크 목록을 JSON 형태로 변환하여 SharedPreferences에 저장
                binding.detailIvBookmark.setImageResource(R.drawable.ic_bookmark)
            }
            val jsonData = GsonBuilder().create().toJson(
                bookmarks,
                object : TypeToken<ArrayList<BookMarkDTO>>() {}.type
            )
            editor.putString("bookmark", jsonData) // SharedPreferences에 push
            editor.apply() // SharedPreferences 적용
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
       

        // 백버튼 설정
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun statusBarBlack90(){
        with(requireActivity()){
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
            window.statusBarColor = ContextCompat.getColor(this, R.color.black_90)
        }
    }

    private fun statusBarWhite(){
        with(requireActivity()){
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        // bottom navi show
        val mainAct = activity as MainActivity
        mainAct.HideBottomNavi(false)
    }


}