package com.kc.marvelapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.kc.marvelapp.R
import com.kc.marvelapp.ui.theme.DarkBlue

@Composable
fun ChipText(
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(8.dp).background(DarkBlue),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = DarkBlue,
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.white)
        )
    ){
        CustomText(text = title, modifier = modifier
            .padding(8.dp)
            .background(DarkBlue))
    }
}