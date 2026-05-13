package com.example.sdiazmusicapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.sdiazmusicapp.Models.Album
import com.example.sdiazmusicapp.Models.albumsList
import com.example.sdiazmusicapp.ui.theme.DarkPurple
import com.example.sdiazmusicapp.ui.theme.SDiazMusicAppTheme

@Composable
fun AlbumComponent (
    album: Album,
    onClick: () -> Unit = { }
)
{
    Box(
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(30.dp))
            .width(200.dp)
            .height(150.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter,
    ) {
        AsyncImage(
            model = album.image,
            contentDescription = "Album Image",
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            placeholder = null,
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .padding(bottom = 15.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(75.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(DarkPurple.copy(alpha = 0.8f)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .padding(start = 10.dp, end = 0.dp)
                    .weight(2f)
            ) {
                //Titulo
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                //Autor
                Text(
                    text = album.artist,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            //Icono de play
            Icon(
                imageVector = Icons.Default.PlayCircle,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun AlbumsPreview(){
    SDiazMusicAppTheme{
        AlbumComponent(
            album = albumsList[0]
        )
    }
}
