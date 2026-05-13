package com.example.sdiazmusicapp.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sdiazmusicapp.Components.HeaderDetail
import com.example.sdiazmusicapp.Components.ReproductorComponent
import com.example.sdiazmusicapp.Components.randomSongs
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
        album?.let { albumData ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Usamos LazyColumn como contenedor principal para mejor rendimiento
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEDE7F6)) // Fondo lavanda claro
                ) {
                    // Sección de Header
                    item {
                        HeaderDetail(album = albumData, navController = navController)
                    }

                    // Sección de Información
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(Color.White)
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = "About this album",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2D1E5F)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = albumData.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(25.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Artist: ",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF2D1E5F)
                                )
                                Text(
                                    text = albumData.artist,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2D1E5F)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Tracks",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2D1E5F)
                            )
                        }
                    }

                    // Lógica del ciclo para 10 canciones ficticias
                    items(10) { index ->
                        randomSongs(
                            album = albumData,
                            navController = navController,
                            index = index // El index aumenta automáticamente (0, 1, 2...)
                        )
                    }

                    // Espacio final para que el reproductor no tape la última canción
                    item { Spacer(modifier = Modifier.height(110.dp)) }
                }

                // Componente Reproductor sobrepuesto
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                ) {
                    ReproductorComponent(album = albumData)
                }
            }
        }
    }
}
