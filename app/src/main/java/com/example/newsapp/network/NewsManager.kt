package com.example.newsapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsManager(private val service: NewsService) {
    private val _newsResponse= mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get()=remember{
            _newsResponse
        }///newsResponse primeste toate valoriile lui _newsResponse


    val sourceName= mutableStateOf("abc-news")
    val selectedCategory:MutableState<ArticleCategory?> = mutableStateOf(null)
    private val _getArticleBySource= mutableStateOf(TopNewsResponse())
    val getArticleBySource:MutableState<TopNewsResponse>
    @Composable get()=remember{
        _getArticleBySource
    }

    val query= mutableStateOf("")
    private val _searchedNewsResponse= mutableStateOf(TopNewsResponse())
    val searchedNewsResponse:MutableState<TopNewsResponse>
        @Composable get()=remember{
            _searchedNewsResponse
        }



    suspend fun getArticles(country:String)
    :TopNewsResponse = withContext(Dispatchers.IO){
      service.getTopArticles(country=country)


    }
    suspend fun getArticlesByCategory(category:String):TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticlesByCategory(category=category)

    }

    suspend fun getArticlesBySources(source:String):TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticlesBySource(source)

    }

    suspend fun getArticleByQuery(query: String):TopNewsResponse =
         withContext(Dispatchers.IO){
         service.getArticlesByQuery(query)

    }
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category=category)
        selectedCategory.value = newCategory

    }
}