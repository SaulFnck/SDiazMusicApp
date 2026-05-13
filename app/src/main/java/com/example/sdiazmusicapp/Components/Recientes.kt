package com.example.sdiazmusicapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.sdiazmusicapp.Models.Album
import com.example.sdiazmusicapp.Models.albumsList
import com.example.sdiazmusicapp.ui.theme.SDiazMusicAppTheme
import org.w3c.dom.Text

@Composable
fun Recientes (
    album: Album,
    onClick: () -> Unit = { }
)
{
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(75.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
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
                    text = album.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                Text(
                    text = "${album.artist} • Popular Song",
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
fun RecientesPreview(){
    SDiazMusicAppTheme {
        Recientes(albumsList[1])
    }
}