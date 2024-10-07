package com.example.newsapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.network.Api
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.theme.MainViewModel

@Composable
fun searchBar(query:MutableState<String>,viewModel: MainViewModel){
   val localFocusManager= LocalFocusManager.current
   Card(
     elevation=6.dp,
     shape= RoundedCornerShape(4.dp),
     modifier= Modifier
         .fillMaxWidth()
         .padding(8.dp),
     backgroundColor = MaterialTheme.colors.primary
   ){
     TextField(value = query.value, onValueChange ={
         query.value=it },modifier=Modifier.fillMaxWidth(),
         label={
             Text(text="search",color= Color.White)
         },
         keyboardOptions = KeyboardOptions(
             keyboardType = KeyboardType.Text,
             imeAction = ImeAction.Search),
         leadingIcon = {
             Icon(imageVector = Icons.Filled.Search,contentDescription = null,
                 tint= Color.White)
         },
         trailingIcon = {
            if(query.value!=""){
                IconButton(onClick = {query.value="" }) {
                    Icon(Icons.Default.Close,contentDescription = null, tint = Color.White)
                }
            }
         },
         textStyle = TextStyle(color= Color.White, fontSize = 18.sp),
         keyboardActions = KeyboardActions(
             onSearch = {
                 if(query.value!=""){
                     viewModel.getSearchedArticles(query.value)
                 }
                 localFocusManager.clearFocus()
             }

         ),
         colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
         )

   }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview(){
    searchBar(query = mutableStateOf(""), viewModel() )
}
