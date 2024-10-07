package com.example.newsapp.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.components.ErrorUI
import com.example.newsapp.components.LoadingUI
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sources(viewModel: MainViewModel,
            isLoading:MutableState<Boolean>,
            isError: MutableState<Boolean>){
   val items= listOf(
       "TechCrunch" to "techcrunch",
       "TalkSport" to "talkSport",
       "Business Insider" to "business-insider",
       "Reuters" to "reuters",
       "Politico" to "politico",
       "TheVerge" to "the-verge")

    Scaffold(topBar = {
        TopAppBar(title={
            Text(text = "${viewModel.sourceName.collectAsState().value} Source")},
            actions = {
                var menuExpanded by remember {
                    mutableStateOf(false)
                }
                IconButton(onClick = { menuExpanded=true }) {
                    Icon(Icons.Default.MoreVert,contentDescription = null)
                }
                MaterialTheme(shapes=MaterialTheme
                    .shapes
                    .copy(medium=RoundedCornerShape(16.dp))){
                    DropdownMenu(expanded = menuExpanded ,
                        onDismissRequest = { menuExpanded=false}) {
                        items.forEach {
                            DropdownMenuItem(onClick = {
                                viewModel.sourceName.value=it.second
                                viewModel.getArticleBySource()
                                menuExpanded=false
                            }) {
                              Text(it.first, color = Color.Black)
                            }
                        }
                    }
                }
            }

        )
    }) {
        when{
            isLoading.value->{
                LoadingUI()
            }
            isError.value->{
                ErrorUI()
            }else->{
            viewModel.getArticleBySource()
            val articles=viewModel.getArticleBySource.collectAsState().value
            SourceContent(articles=articles.articles?: listOf())
            }
        }








    }
}
@Composable
fun SourceContent(articles:List<TopNewsArticle>){
    val uriHandler= LocalUriHandler.current


    LazyColumn{
        items(articles){article->
            val annotatedString= buildAnnotatedString {///WTF
                pushStringAnnotation(
                    tag="URL",
                    annotation = article.url?:"newsapi.org"
                )
                withStyle(style= SpanStyle(color= colorResource(id = R.color.purple_500)),
                      /*textDecoration=TextDecoration.Underline*/
                    ){append("Read Full Article Here")
                     }
            }
           Card(backgroundColor = colorResource(id = R.color.purple_700),
               elevation = 6.dp,
               modifier= Modifier.padding(8.dp)){
             Column(modifier= Modifier
                 .height(200.dp)
                 .padding(start = 8.dp, end = 8.dp),
                 verticalArrangement = Arrangement.SpaceBetween) {
                 Text(
                     text=article.title?:"Not Available",
                     fontWeight = FontWeight.Bold,
                     maxLines = 2,
                     overflow = TextOverflow.Ellipsis
                 )
                 Text(
                     text=article.description?:"Not Available",
                     maxLines = 3,
                     overflow = TextOverflow.Ellipsis
                 )
                 Card(backgroundColor = Color.White, elevation = 6.dp,
                     modifier = Modifier.padding(8.dp)){
                     ClickableText(text = annotatedString,
                         modifier = Modifier.padding(8.dp),
                         onClick ={
                         annotatedString.getStringAnnotations(it,it).firstOrNull()?.let{
                             result->
                             if(result.tag=="URL"){
                                 uriHandler.openUri(result.item)
                             }
                         }

                     } )
                 }
             }
           }

        }
    }
}