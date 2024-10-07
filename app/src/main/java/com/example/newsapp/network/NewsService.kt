package com.example.newsapp.network

import com.example.newsapp.models.Source
import com.example.newsapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface NewsService {
    @GET("top-headlines")
   suspend fun getTopArticles(@Query("country")country:String): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategory(@Query("category")category:String):TopNewsResponse

    @GET("everything")
    suspend fun getArticlesBySource(@Query("sources")source: String):TopNewsResponse

    @GET("everything")
    suspend fun getArticlesByQuery(@Query("q")query: String):TopNewsResponse


}
























/*
interface NewsService {
    @GET("top-headlines")
    fun getTopArticles(@Query("country")country:String,
                       @Query("apiKey")apiKey:String): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(@Query("category")category:String,
                              @Query("apiKey")apiKey:String):Call<TopNewsResponse>
}*/
