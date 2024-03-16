package com.littlelemon.littlelemonappcoursera

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController){
    Column {
        Text("Home Screen")
        Button(onClick = { navController.navigate(Profile.route) }) {
            Text("Go to Profile Page")
        }
    }


}