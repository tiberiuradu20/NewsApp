package com.example.newsapp.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.R
import com.example.newsapp.components.ErrorUI
import com.example.newsapp.components.LoadingUI
import com.example.newsapp.components.searchBar
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNewsApp(navController:NavController,articles: List<TopNewsArticle>,
               query:MutableState<String>,viewModel: MainViewModel,
               isLoading:MutableState<Boolean>,
               isError:MutableState<Boolean>){
    Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Text(text="Top News", fontWeight = FontWeight.SemiBold)
        searchBar(query = query, viewModel = viewModel)
        val searchedText=query.value
        val resultList= mutableListOf<TopNewsArticle>()
      if(searchedText!=""){
          resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?:articles)
      }
        else{
            resultList.addAll(articles)
      }
        when{
            isLoading.value->{
                LoadingUI()
            }
            isError.value->{
                ErrorUI()
            }else->{
            LazyColumn(modifier=Modifier.wrapContentSize()){
                items(resultList.size){index->
                    TopNewsItem(article = resultList[index],
                        onNewsClick={navController.navigate("DetailScreen/$index")})

                }
            }
            }

        }




    }

}
@Composable
fun TopNewsItem(article: TopNewsArticle,onNewsClick:()->Unit={}){
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }){
    CoilImage(
       imageModel = article.urlToImage,
       contentScale = ContentScale.Crop,
       error= ImageBitmap.imageResource(R.drawable.breaking_news) ,
       placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news,
         ),
        modifier = Modifier.fillMaxWidth()
    )
        Column(modifier= Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {

            article.publishedAt?.let{
            Text(text=MockData.stringToDate(article.publishedAt!!).getTimeAgo() ,color= Color.Black,
                fontWeight = FontWeight.Bold)
        }
            Spacer(modifier = Modifier.height(80.dp))
            article.title?.let{
                Text(text=article.title!!,color=Color.White, fontWeight = FontWeight.SemiBold)
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun TopNewsAppPreview(){
    TopNewsItem(article = TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z")
    )
}