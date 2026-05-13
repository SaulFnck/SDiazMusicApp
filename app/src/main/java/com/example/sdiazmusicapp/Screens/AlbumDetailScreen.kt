package com.example.sdiazmusicapp.Screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sdiazmusicapp.Components.HeaderDetail
import com.example.sdiazmusicapp.Models.Album
import com.example.sdiazmusicapp.Services.albumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Composable
fun AlbumDetailScreen(
    innerPadding: PaddingValues = PaddingValues(15.dp),
    navController: NavController = rememberNavController(),
    id: String
){

    //Gradiente de fondo
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            Color.White
        )
    )

    val BASE_URL = "https://musicapi.pjasoft.com/"
    var album by remember { mutableStateOf<Album?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(key1 = id)
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
                Log.d("AlbumDetailScreen", "Iniciando peticion a: ${BASE_URL}api/albums/$id")
                val service = retrofit.create(albumService::class.java)
                service.getAlbum(id)
            }

            Log.i("AlbumDetailScreen", "Datos recibidos con exito: ${result.title}")
            album = result
            isLoading = false
        } catch (e: Exception) {
            Log.e("AlbumDetailScreen", "ERROR EN PETICION: ${e.message}")
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
    } else{
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            album?.let {
                //Header
                HeaderDetail(album = it, navController = navController)

                //About album

                //artist

                //Row randoms songs



            }
        }
    }
}
