package com.littlelemon.littlelemonappcoursera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.littlelemon.littlelemonappcoursera.ui.theme.LittleLemonAppCourseraTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url: String = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response: MenuNetworkdata? = client.get(url).body()
        return response?.menu ?: listOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDataBase.getDatabase(this)

        // fetch menu items in a separate thread
        lifecycleScope.launch {
            val items = fetchMenu()
            val itemsDb = items.map {
                MenuItemDb(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    category = it.category,
                    imgUrl = it.imgUrl
                )
            }
            withContext(IO){
                database.menuItemDao().insertAllItems(itemsDb)
            }

        }


        setContent {
            LittleLemonAppCourseraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var menuItems = database.menuItemDao().getAll().observeAsState(listOf<MenuItemDb>()).value
                    LittleLemon(menuItems)
                }
            }
        }
    }
}

@Composable
fun LittleLemon(menuItems: List<MenuItemDb>){
    val navController = rememberNavController()
    Navigation(navController)

}