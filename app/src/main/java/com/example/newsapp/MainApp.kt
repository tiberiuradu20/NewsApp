package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.Repository
import com.example.newsapp.network.Api
import com.example.newsapp.network.NewsManager

class MainApp:Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }
    val repository by lazy{
        Repository(manager)
    }

}