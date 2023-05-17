package com.kc.marvelapp.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CustomText(
    text: String = "",
    color: Color = MaterialTheme.colors.onBackground,
    fontStyle: FontStyle = FontStyle.Normal,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
    style: TextStyle = (MaterialTheme.typography).body1
) {
    Text(
        text = text,
        color = color,
        fontStyle = fontStyle,
        overflow = overflow,
        maxLines = maxLines,
        style = style,
        modifier = modifier
    )
}
