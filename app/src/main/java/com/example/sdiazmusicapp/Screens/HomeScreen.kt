package com.example.sdiazmusicapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sdiazmusicapp.Components.Header
import com.example.sdiazmusicapp.Models.Album
import com.example.sdiazmusicapp.Services.albumService
import com.example.sdiazmusicapp.ui.theme.SDiazMusicAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import com.example.sdiazmusicapp.Components.AlbumComponent
import com.example.sdiazmusicapp.Components.Recientes
import com.example.sdiazmusicapp.Components.ReproductorComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@Composable
fun HomeScreen (
    innerPadding: PaddingValues = PaddingValues(15.dp),
    navController: NavController = rememberNavController()
    ){

    //Gradiente de fondo
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            Color.White
        )
    )

    val BASE_URL = "https://musicapi.pjasoft.com/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = true)
    {
        try {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val result = withContext(Dispatchers.IO) {
                Log.d("HomeScreen", "Iniciando peticion a: ${BASE_URL}api/albums")
                val service = retrofit.create(albumService::class.java)
                service.getAlbums() 
            }

            Log.i("HomeScreen", "Datos recibidos con exito: ${result.size} albums")
            albums = result
            isLoading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "ERROR EN PETICION: ${e.message}")
            errorMessage = e.localizedMessage
            isLoading = false
        }
    }

    if(isLoading)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(text = "Cargando música...", modifier = Modifier.padding(top = 8.dp))
            }
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error de conexión: $errorMessage", color = Color.Red)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(horizontal = 15.dp)
        ) {
            item {
                Box(modifier = Modifier.height(50.dp)) // Espaciador superior
                Header()
            }

            // SECCIÓN ALBUMS HORIZONTAL
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Albums",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    items(albums) { album ->
                        AlbumComponent(album = album)
                    }
                }
            }

            // SECCIÓN RECIENTES (VERTICAL)
            item {
                Text(
                    text = "Recently Played",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(albums) { album ->
                Recientes(album = album)
            }
            //Reproductor
            item {
                var album = albums[0]
                ReproductorComponent(album)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    SDiazMusicAppTheme {
        HomeScreen()
    }
}
