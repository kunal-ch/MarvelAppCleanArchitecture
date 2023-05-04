package com.kc.marvelapp.presentation.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kc.marvelapp.ui.theme.DarkBlue
import com.kc.marvelapp.ui.theme.TextWhite

@Composable
fun Toolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier,
        backgroundColor = DarkBlue,
        contentColor = TextWhite,
        elevation = 2.dp
    )
}