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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun OnboardingScreen(navController: NavHostController){
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
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
            "Let's get to know you",
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColors.darkGreen)
                .height(80.dp)
                .wrapContentSize()
        )

        Text(
            "Personal Information",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .wrapContentSize(Alignment.CenterStart)
                .padding(start = 20.dp, top = 30.dp)
            )

        InputTextField("First name", firstName) {firstName=it}
        InputTextField("Last name", lastName) {lastName=it}
        InputTextField("Email", email) {email=it}

        Button(
            onClick = {handleInfoSubmission(navController, context, firstName, lastName, email)},
            colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.yellow, contentColor = Color.Black),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Text("Register")
        }

    }
}

fun handleInfoSubmission(navController: NavHostController,context: Context, firstName: String, lastName: String, email: String){
    // perform input validation
    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()){
        Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_LONG).show()
        return
    }

    // save user info in shared preferences
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply {
        putString("firstName", firstName)
        putString("lastName", lastName)
        putString("email", email)
    }.apply()

    // Registration Success Toast
    Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()

    // navigate to home screen
    navController.navigate(Home.route)

}

@Composable
fun InputTextField(fieldName:String, fieldValue:String, onClickFn: (String)->Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(fieldName)
        TextField(
            value = fieldValue,
            onValueChange = onClickFn,
            modifier = Modifier.fillMaxWidth()
        )
    }
}