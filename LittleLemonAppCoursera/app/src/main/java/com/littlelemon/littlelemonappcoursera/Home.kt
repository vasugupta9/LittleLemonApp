package com.littlelemon.littlelemonappcoursera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(navController: NavHostController){
    val context = LocalContext.current
    val database = AppDataBase.getDatabase(context)
    val menuItems by database.menuItemDao().getAll().observeAsState()
    var lastCategoryClicked by remember { mutableStateOf("") }
    var searchPhrase by remember { mutableStateOf("") }
    var filteredMenuItems: List<MenuItemDb> = when(lastCategoryClicked){
        "starters" -> menuItems?.filter { it.category == "starters" } ?: listOf()
        "mains" -> menuItems?.filter { it.category == "mains" } ?: listOf()
        "desserts" -> menuItems?.filter { it.category == "desserts" } ?: listOf()
        "drinks" -> menuItems?.filter { it.category == "drinks" } ?: listOf()
        else -> menuItems ?: listOf()
        }
    if (searchPhrase.isNotBlank()){
        filteredMenuItems = menuItems?.filter { it.title.contains(searchPhrase, ignoreCase = true) } ?: listOf()
    }

    Column {
        TopPanel(navController)
        HeroPanel(searchPhrase) { searchPhrase = it;lastCategoryClicked = "" }
        CategoriesPanel { lastCategoryClicked = it; searchPhrase="" }
        DishesPanel(filteredMenuItems)
    }
}

@Composable
fun CategoriesPanel(onClickFn: (String)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text("ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.cloud, contentColor = Color.Black),
                modifier = Modifier.padding(1.dp),
                onClick = { onClickFn("starters") }
            ) {
                Text(text="Starters", fontSize = 10.sp)
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.cloud, contentColor = Color.Black),
                modifier = Modifier.padding(1.dp),
                onClick = { onClickFn("mains") }
            ) {
                Text("Mains", fontSize = 10.sp)
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.cloud, contentColor = Color.Black),
                modifier = Modifier.padding(1.dp),
                onClick = { onClickFn("desserts") }
            ) {
                Text("Deserts", fontSize = 10.sp)
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColors.cloud, contentColor = Color.Black),
                modifier = Modifier.padding(1.dp),
                onClick = { onClickFn("drinks") }
            ) {
                Text("Drinks", fontSize = 10.sp)
            }
        }

    }

}


@Composable
fun DishesPanel(filteredMenuItems: List<MenuItemDb>){
    LazyColumn(state= rememberLazyListState()) {
        itemsIndexed(filteredMenuItems){ _, item ->
            MenuItemComposable(item = item)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemComposable(item: MenuItemDb){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 6.dp)) {
        Column {
            Text(text = item.title, style=MaterialTheme.typography.headlineMedium)
            Text(text = item.description, style=MaterialTheme.typography.bodyMedium, modifier = Modifier
                .fillMaxWidth(0.75F)
                .padding(top = 5.dp, bottom = 5.dp))
            Text(text = "$${item.price}", style=MaterialTheme.typography.bodySmall)
        }
        GlideImage(model = item.imgUrl, contentDescription = item.title )
    }

    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = LittleLemonColors.yellow
    )

}

@Composable
fun TopPanel(navController: NavHostController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile",
            modifier = Modifier.clickable { navController.navigate(Profile.route) }
        )
    }
}

@Composable
fun HeroPanel(searchPhrase:String, onValChangedFunc: (String)->Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .background(LittleLemonColors.darkGreen)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColors.yellow,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        )
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 20.sp,
            color = LittleLemonColors.cloud,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start=20.dp,end=20.dp,top=10.dp, bottom=10.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(2f),
                color = LittleLemonColors.cloud
            )


            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Upper Panel Image",
                modifier = Modifier
                    .height(100.dp)
                    .weight(0.8f)
                    .clip(RoundedCornerShape(30.dp))
            )
        }

        TextField(
            value = searchPhrase,
            onValueChange =  onValChangedFunc ,
            placeholder = {Text("Enter Search Phrase")},
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )

    }

}

