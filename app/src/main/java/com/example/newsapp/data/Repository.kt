package com.example.newsapp.data

import com.example.newsapp.network.NewsManager
import retrofit2.http.Query

class Repository(val manager:NewsManager) {
    suspend fun getArticles()=manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String)=manager.getArticlesByCategory(category)
    suspend fun getArticlesBySources(source:String)
    = manager.getArticlesBySources(source=source)
    suspend fun getArticleByQuery(query: String)=manager.getArticleByQuery(query)
}