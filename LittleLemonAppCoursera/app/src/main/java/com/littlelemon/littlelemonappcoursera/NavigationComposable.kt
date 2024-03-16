package com.littlelemon.littlelemonappcoursera

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val userLoggedIn = sharedPreferences.all.isNotEmpty()
    val startDestination = if(userLoggedIn) Home.route else Onboarding.route
    NavHost(navController = navController, startDestination = startDestination){
        composable(Home.route){
            HomeScreen(navController)
        }
        composable(Onboarding.route){
            OnboardingScreen(navController)
        }
        composable(Profile.route){
            ProfileScreen(navController)
        }
    }
}