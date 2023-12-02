package com.example.pinapphomework.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pinapphomework.NewsClickListener
import com.example.pinapphomework.R
import com.example.pinapphomework.ui.theme.News

val TAG = "NewsListFragment"

class NewsListFragment : Fragment() {

    private var newsClickListener: NewsClickListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NewsClickListener) newsClickListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTvViews(view)
    }

    private fun initTvViews(view: View) {
        val tvFirstNews: TextView = view.findViewById(R.id.tv_first_news)
        tvFirstNews.setOnClickListener { onNewsClicked(index = 1, view = it) }

        val tvSecondNews: TextView = view.findViewById(R.id.tv_second_news)
        tvSecondNews.setOnClickListener { onNewsClicked(index = 2, view = it) }

        val tvThirdNews: TextView = view.findViewById(R.id.tv_third_news)
        tvThirdNews.setOnClickListener { onNewsClicked(index = 3, view = it) }

        val tvForthNews: TextView = view.findViewById(R.id.tv_fourth_news)
        tvForthNews.setOnClickListener { onNewsClicked(index = 4, view = it) }

        val tvFifthNews: TextView = view.findViewById(R.id.tv_fifth_news)
        tvFifthNews.setOnClickListener { onNewsClicked(index = 5, view = it) }

        val tvNewsOne: TextView = view.findViewById(R.id.tv_sixth_news)
        tvNewsOne.setOnClickListener { onNewsClicked(index = 6, view = it) }
    }

    private fun onNewsClicked(index: Int, view: View) {
        if (view !is TextView) return

        val title = view.text.toString()

        val news = News(
            title = title,
            author = "Author: $index",
            data = "Date: $index"
        )

        Log.e(TAG, "News clicked = $news")
        newsClickListener?.onNewsClick(news)
    }

}