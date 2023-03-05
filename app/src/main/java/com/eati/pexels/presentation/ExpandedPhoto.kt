package com.eati.pexels.presentation



import androidx.compose.foundation.layout.*

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eati.pexels.domain.Photo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*


@Composable
fun ExpandedPhoto(photo: Photo?) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (photo != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier= Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign= TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = photo.alt)
            AsyncImage(
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.CenterHorizontally),
                model = photo.sourceURL,
                contentDescription= photo.alt)
            Row(modifier = Modifier
                .padding(top = 20.dp, bottom = 15.dp)
                .align(Alignment.CenterHorizontally)){
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.primary
                )
                Text(modifier = Modifier.padding(top = 4.dp),text = photo.photographer)
            }
            Row(modifier = Modifier
                .padding(vertical = 15.dp)
                .align(Alignment.CenterHorizontally)){
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.primary
                )
                Text(modifier = Modifier.padding(top = 4.dp), text = photo.width.toString()+"x"+photo.height.toString()+" pixels" )
            }

        }
    }


}
