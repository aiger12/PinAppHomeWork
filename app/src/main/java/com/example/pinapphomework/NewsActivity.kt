package com.example.pinapphomework

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pinapphomework.fragments.NewDetailsFragment
import com.example.pinapphomework.fragments.NewsListFragment
import com.example.pinapphomework.ui.theme.News

class NewsActivity : AppCompatActivity(), NewsClickListener{

    val TAG = "NewsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initViews()
    }

    private fun initViews(){
        val fragment = NewsListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_list, fragment)
            .commit()
    }

    override fun onNewsClick(news: News) {
        Log.e(TAG,"News Details: $news")

        val newsDetails=NewDetailsFragment.newInstance(news)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_details, newsDetails)
            .addToBackStack(null)
            .commit()
    }

}