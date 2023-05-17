package com.kc.marvelapp.presentation.characterListing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.presentation.components.CustomText
import com.kc.marvelapp.util.Utils
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CharacterItem(
    character: ComicCharacter,
    modifier: Modifier = Modifier
) {
    Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
    ) {
        CoilImage(
            imageModel = character.thumbnail.path+"."+character.thumbnail.extension,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomText(
                    text = character.name,
                    style = (MaterialTheme.typography).h6,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            CustomText(
                text = Utils.getDate(character.modified),
                fontStyle = FontStyle.Italic,
            )
        }
    }
}