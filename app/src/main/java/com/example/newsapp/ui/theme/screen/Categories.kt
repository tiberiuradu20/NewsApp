package com.example.newsapp.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.components.ErrorUI
import com.example.newsapp.components.LoadingUI
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.models.getAllArticlesCategories
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit={},
               viewModel: MainViewModel,
               isLoading:MutableState<Boolean>,
               isError:MutableState<Boolean>){
   val tabsItems=getAllArticlesCategories()
    Column {
        when{
            isLoading.value->{
                LoadingUI()
            }
            isError.value->{
                ErrorUI()
            }else->{
            LazyRow{
                items(tabsItems.size){
                    val category=tabsItems[it]
                    CateogyTab(category = category.categoryName,
                        onFetchCategory =onFetchCategory,
                        isSelected = viewModel.selectedCategory.collectAsState().value == category
                    )


                }
            }
            }
        }


        ArticleContent(articles = viewModel.getArticleCategory.collectAsState().value.articles ?:
        listOf()
        )
    }
}
@SuppressLint("SuspiciousIndentation")
@Composable//asta este utila pentru interfata mea cu zile
fun CateogyTab(category:String,
               isSelected:Boolean=false,onFetchCategory:(String)->Unit){
  val background=if(isSelected) colorResource(id = R.color.purple_200)else
   colorResource(id = R.color.purple_700)
    Surface (
      modifier= Modifier
          .padding(horizontal = 4.dp, vertical = 16.dp)
          .clickable { onFetchCategory(category) }  ,
      shape=MaterialTheme.shapes.small,
        color=background
    ){
        Text(text=category,
            style=MaterialTheme.typography.body2,
            color= Color.White,
            modifier=Modifier.padding(8.dp))
    }
}
@Composable
fun ArticleContent(articles:List<TopNewsArticle>,modifier:Modifier= Modifier){
    LazyColumn{
        items(articles){
            article->
            Card(modifier.padding(8.dp),border= BorderStroke(2.dp,
                color= colorResource(id = R.color.purple_500 ))){
                Row(modifier= modifier
                    .padding(8.dp)
                    .fillMaxWidth()){
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier=Modifier.size(100.dp),
                        placeHolder = painterResource(id =
                        R.drawable.breaking_news),
                        error = painterResource(id = R.drawable.breaking_news))
                    Column(modifier=modifier.padding(8.dp)) {
                        Text(text=article.title?:"Not Available",
                            fontWeight = FontWeight.Bold,
                            maxLines=3,
                            overflow = TextOverflow.Ellipsis
                            )
                        Row(
                            modifier=modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(text=article.author?:"Not Available")
                            Text(text=MockData.
                            stringToDate(article.publishedAt?:
                            "2021-11-10T14:25:20Z").getTimeAgo())
                        }

                    }
                }

            }


        }
    }
}
@Preview(showBackground = true)
@Composable
fun ArticleContentPreview(){
    ArticleContent(articles =
    listOf(
        TopNewsArticle(
            author = "Mike Florio",
            title = "Aaron Rodgers violated COVID protocol by doing maskless indoor press conferences - NBC Sports",
            description = "Packers quarterback Aaron Rodgers has been conducting in-person press conferences in the Green Bay facility without wearing a mask. Because he was secretly unvaccinated, Rodgers violated the rules.",
            publishedAt = "2021-11-04T03:21:00Z"
    )
    ))
}
