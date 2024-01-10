package com.example.pinapphomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.pinapphomework.R

class FavoriteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

//        setupVideoClickListener(view)

        return view
    }

//    private fun setupVideoClickListener(view: View) {
//        val videoItem = view.findViewById<View>(R.id.videoView) // Replace with your video item ID
//        videoItem.setOnClickListener {
//            showMaxPlayer(view)
//        }
//    }

    private fun showMaxPlayer(view: View) {
//        val maxPlayerContainer = view<FrameLayout>(R.layout.max_player)
//        val maxPlayerView = layoutInflater.inflate(R.layout.layout_max_player, maxPlayerContainer, false)
//        maxPlayerContainer.addView(maxPlayerView)
//        maxPlayerContainer.visibility = View.VISIBLE

        // Setup close button or other controls in maxPlayerView
        // ...
    }

}