package com.example.abasgo.ui.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.abasgo.ui.getIconRes
import com.example.abasgo.ui.state.AppPanel
import com.example.abasgo.ui.theme.Attention
import com.example.abasgo.ui.theme.DarkGreen
import com.example.abasgo.ui.theme.LightGreen
import com.example.abasgo.ui.theme.White

@Composable
fun NavigationBar(
    current: AppPanel,
    onSelect: (AppPanel) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(70.dp))
            .background(DarkGreen)
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppPanel.entries.forEach { destination ->

            val isSelected = destination == current

            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) LightGreen else DarkGreen,
                        shape = CircleShape
                    )
                    .clickable { onSelect(destination) }
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()

                ) {
                    Icon(
                        painter = painterResource(id = getIconRes(destination.name)),
                        contentDescription = destination.label,
                        tint = if (isSelected) Attention else White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = destination.label,
                        textAlign = TextAlign.Center,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}