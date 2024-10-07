package com.example.newsapp.models

import androidx.compose.runtime.Composable
import com.example.newsapp.models.ArticleCategory.*


enum class ArticleCategory(val categoryName:String) {
    BUSINESS("business"),
    ENTERTAIMENT("entertaiment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun getAllArticlesCategories():List<ArticleCategory>{
    return listOf(ArticleCategory.BUSINESS,
        ArticleCategory.ENTERTAIMENT,
        ArticleCategory.GENERAL,ArticleCategory.HEALTH,ArticleCategory.SCIENCE,
        ArticleCategory.SPORTS,ArticleCategory.TECHNOLOGY)
}
fun getArticleCategory(category:String):ArticleCategory?{
    val map=values().associateBy(ArticleCategory::categoryName)
    return map[category]
}