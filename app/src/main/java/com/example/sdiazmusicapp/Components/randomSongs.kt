package com.example.sdiazmusicapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.sdiazmusicapp.Models.Album
import com.example.sdiazmusicapp.Models.albumsList
import com.example.sdiazmusicapp.ui.theme.SDiazMusicAppTheme

@Composable
fun randomSongs(album: Album, navController: NavController,index: Int) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.SpaceAround
        ) {

            Row(
                modifier = Modifier
                    .weight(2f)
            )
            {
                AsyncImage(
                    model = album.image,
                    contentDescription = "Album Image",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .width(50.dp)
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Column(

                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                {
                    Text(
                        text = "${album.title} • Track ${index + 1} ",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                    Text(
                        text = "${album.artist}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }


            //Icono tres puntos
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                tint = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }


@Preview
@Composable
fun randomSongsPreview() {
    SDiazMusicAppTheme {
        randomSongs(album = albumsList[0], navController = rememberNavController(),index = 0)
    }
}