package com.example.newsapp.ui.theme.screen

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.newsapp.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detailScreen(scrollState: ScrollState,article: TopNewsArticle,navController: NavController){

    Scaffold (topBar={
      DetailTopAppBar(onBackPressed ={navController.popBackStack()} )
    }){
        Column(modifier= Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(start = 8.dp, end = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text="Detail Screen", fontWeight = FontWeight.SemiBold)
         CoilImage(
             imageModel = article.urlToImage,
             contentScale=ContentScale.Crop,
             error= ImageBitmap.imageResource(R.drawable.breaking_news),
             placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news),
             modifier = Modifier.fillMaxWidth()

         )
            Row(modifier= Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                InfoWithIcon(icon = Icons.Default.Edit, info =article.author?:"Not available")
                InfoWithIcon(icon = Icons.Default.DateRange,
                    info=MockData.stringToDate(article.publishedAt!!).getTimeAgo())
            }
            Text(text=article.title?:"Not available", fontWeight = FontWeight.Bold)
            Text(text=article.description?:"Not available",modifier=Modifier.padding(top=16.dp))
        }

    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(onBackPressed:()->Unit={}){
    TopAppBar(title = { Text(text="Detail Screen",
        fontWeight = FontWeight.SemiBold )},
        navigationIcon = {
            IconButton(onClick = { onBackPressed()}) {
             Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}
@Composable
fun InfoWithIcon(icon:ImageVector,info:String){
    Row{
        Icon(icon, contentDescription = "Author",
            tint = colorResource(id = R.color.purple_500))
        Text(text=info)
    }
}


@Preview(showBackground = true)
@Composable
fun detailScreenPreview(){
    detailScreen(
        rememberScrollState(), TopNewsArticle(
            author="Tiberiu Baesu",
            title="Pe culmile succesului",
            description="Everything works very well",
            publishedAt = "2021-11-04T04:42:40Z"
        ), rememberNavController()
    )///ce este un rememberNavController?
                                         // In afara de faptul ca in cazul nostru este mai mult dummy
}