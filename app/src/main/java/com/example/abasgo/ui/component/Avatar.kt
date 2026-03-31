package com.example.abasgo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.abasgo.R

@Composable
fun Avatar() {
    Image(
        painter = painterResource(id = R.drawable.default_avatar),
        contentDescription = "Аватар",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(75.dp)
    )
}