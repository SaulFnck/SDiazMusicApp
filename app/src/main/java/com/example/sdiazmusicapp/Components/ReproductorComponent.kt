package com.example.sdiazmusicapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
fun ReproductorComponent(
    album: Album,
){
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(DarkPurple),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.image,
                contentDescription = "Album Image",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(50.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
            ) {
                Text(
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Text(
                    text = album.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        // Botón de Play Circular
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(55.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = DarkPurple,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Preview
@Composable
fun ReproductorPreview(){
    SDiazMusicAppTheme{
        ReproductorComponent(albumsList[0])
    }
}
