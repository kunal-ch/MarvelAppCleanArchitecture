package com.kc.marvelapp.presentation.components

import androidx.compose.material.MaterialTheme
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
            CustomText(
                text = title,
                style = (MaterialTheme.typography).h4
            )
        },
        modifier = modifier,
        backgroundColor = DarkBlue,
        contentColor = TextWhite,
        elevation = 2.dp
    )
}