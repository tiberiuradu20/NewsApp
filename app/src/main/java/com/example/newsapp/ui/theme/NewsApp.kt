package com.example.newsapp.ui.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.MockData
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.Api
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.screen.Categories
import com.example.newsapp.ui.theme.screen.Sources
import com.example.newsapp.ui.theme.screen.TopNewsApp
import com.example.newsapp.ui.theme.screen.detailScreen

@Composable
fun newsApp(mainViewModel: MainViewModel){
    val scrollState= rememberScrollState()
    val navController= rememberNavController()

    MainScreen(navController ,scrollState,mainViewModel)
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState,
               mainViewModel: MainViewModel){
    Scaffold(bottomBar = {
       BottomMenu(navController = navController) 
        
    }){///valoare de padding
        Navigation(navController=navController,scrollState, paddingValues = it,
            mainViewModel = mainViewModel)
    }
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState,
               newsManager: NewsManager=NewsManager(Api.retrofitService),
               paddingValues: PaddingValues,
               mainViewModel: MainViewModel){
    val loading by mainViewModel.isLoading.collectAsState()
    val error by mainViewModel.isError.collectAsState()
   val articles= mutableListOf(TopNewsArticle())
   val topArticles= mainViewModel.newsResponse.collectAsState().value.articles
       articles.addAll(topArticles?:listOf())

    Log.d("stiri","$articles")
       articles?.let {//articles nu trebuie sa fie null, ne va distruge aplicatia!!!

           NavHost(navController = navController,
              startDestination = BottomMenuScreen.TopNews.route,
               modifier= Modifier.padding(paddingValues =paddingValues )
              ) {
               val queryState= mutableStateOf(mainViewModel.query.value)
               val isLoading= mutableStateOf(loading)
               val isError= mutableStateOf(error)
               bottomNavigation(navController = navController, articles, query=queryState,
                   viewModel = mainViewModel,isError=isError, isLoading = isLoading)

               composable(///in acest mod putem ajunge la detaliile fiecarui
                   ///articol in parte folosind un index, care este doar
                   ///nr primului articol din lista noastra de articole!!!
                   "DetailScreen/{index}",
                   arguments = listOf(navArgument("index") { type = NavType.IntType })
               ) { navBackStackentry ->
                   val index = navBackStackentry.arguments?.getInt("index")
                index?.let {
                    if(queryState.value!=""){
                        articles.clear()
                        articles.addAll(mainViewModel.searchedNewsResponse.value.articles?: listOf())
                    }else{
                        articles.clear()
                        articles.addAll(mainViewModel.newsResponse.value.articles?: listOf())
                    }
                   val article=articles[index]
                   detailScreen(scrollState=scrollState,article=article,navController=navController )
               }
               }///datorita acestui element pot naviga la ecranul de detalii!!!!!


           }
       }
}
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>,
                                    query:MutableState<String>,
                                     viewModel: MainViewModel,
                                     isLoading:MutableState<Boolean>,
                                     isError:MutableState<Boolean>){
  composable(BottomMenuScreen.TopNews.route){
      TopNewsApp(navController = navController, articles=articles,
          query,viewModel=viewModel,
          isLoading=isLoading, isError=isError)
  }

    composable(BottomMenuScreen.Categories.route){
      viewModel.getArticlesByCategory("business")
      viewModel.onSelectedCategoryChanged("business")

       Categories(viewModel =  viewModel, onFetchCategory = {
           viewModel.onSelectedCategoryChanged(it)
           viewModel.getArticlesByCategory(it)
       }, isLoading = isLoading, isError = isError)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(viewModel=viewModel,isLoading=isLoading,
            isError=isError)
    }

}




