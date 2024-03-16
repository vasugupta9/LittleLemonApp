package com.littlelemon.littlelemonappcoursera

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName  = sharedPreferences.getString("lastName", "") ?: ""
    val email     = sharedPreferences.getString("email", "") ?: ""

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .height(80.dp)
                .fillMaxSize(0.5f)
        )

        Text(
            "Personal Information",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .wrapContentSize(Alignment.CenterStart)
                .padding(start = 20.dp, top = 60.dp)
        )

        UserInfoDisplay(firstName, lastName, email)

        Button(
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(Onboarding.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Text("Log out")
        }

    }

}

@Composable
fun UserInfoDisplay(firstName:String, lastName:String, email:String){

    // firstName
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
        Text("First name")
        TextField(value = firstName, onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
    }


    // lastName
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
        Text("Last name")
        TextField(value = lastName, onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
    }

    // email
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
        Text("Email")
        TextField(value = email, onValueChange = {}, enabled = false, modifier = Modifier.fillMaxWidth())
    }


}

//@Preview
//@Composable
//fun PreviewProfileScreen(){
//    ProfileScreen(navController = null)
//}