package com.example.newsapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun LoadingUI(){
    Box(modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        CircularProgressIndicator()
    }
}
@Composable
fun ErrorUI(){
    Box(modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text="An Error has occured. Please try again")

    }
}