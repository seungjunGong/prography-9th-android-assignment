package com.lagame.cloneunsplash.src

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.lagame.cloneunsplash.R
import com.lagame.cloneunsplash.databinding.FragmentDetailBinding

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
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.detailPhotoIv)

        // cancel button 처리
        binding.detailIvCancel.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
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