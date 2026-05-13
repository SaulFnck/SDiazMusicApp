package com.example.sdiazmusicapp

import  android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sdiazmusicapp.Screens.AlbumDetailScreen
import com.example.sdiazmusicapp.Screens.HomeScreen
import com.example.sdiazmusicapp.ui.theme.SDiazMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SDiazMusicAppTheme {
                val navController = rememberNavController()
                //Logica navegación
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ){
                        // Home
                        composable(route = "home"){
                            HomeScreen(
                                innerPadding = innerPadding,
                                navController = navController
                            )
                        }

                        // Detail
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(
                                navArgument("id"){
                                    type = NavType.StringType
                                    nullable = false
                                }
                            )
                        ){ backStack ->
                            val id = backStack.arguments?.getString("id") ?: ""
                            AlbumDetailScreen(
                                innerPadding = innerPadding,
                                navController = navController,
                                id = id
                            )
                        }
                    }
                }
            }
        }
    }
}
