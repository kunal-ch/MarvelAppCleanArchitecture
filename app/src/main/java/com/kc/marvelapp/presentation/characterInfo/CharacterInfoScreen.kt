package com.kc.marvelapp.presentation.characterInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kc.marvelapp.R
import com.kc.marvelapp.presentation.components.ChipText
import com.kc.marvelapp.presentation.components.CustomText
import com.kc.marvelapp.presentation.components.Toolbar
import com.kc.marvelapp.ui.theme.DarkBlue
import com.kc.marvelapp.util.Utils
import com.skydoves.landscapist.coil.CoilImage

/**
 * Character Detail Screen
 */
@Composable
fun CharacterInfoScreen(
    id: String,
    viewModel: CharacterInfoViewModel = hiltViewModel(),
    vmState: CharacterInfoState = viewModel.state
) {
    val state = vmState
    if(state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            state.character?.let { character ->
                Toolbar(title = stringResource(id = R.string.app_name))
                Divider()
                CoilImage(
                    imageModel = character.thumbnail.path+"."+character.thumbnail.extension,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                        .padding(16.dp)
                ) {
                    // Name
                    CustomText(
                        text = character.name,
                        style = MaterialTheme.typography.h6,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Modified
                    CustomText(
                        text = Utils.getDate(character.modified),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    // Description
                    if (state.character.description.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomText(
                            text = stringResource(R.string.description) + " ${character.description}",
                            maxLines = Int.MAX_VALUE,
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomText(
                        text = stringResource(R.string.comics),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Add Chips
                    LazyColumn() {
                        items(character.comics.items.size){ i->
                            val item = character.comics.items[i]
                            ChipText(title = item.name)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(state.isLoading) {
            CircularProgressIndicator()
        } else if(state.error != null) {
            CustomText(
                text = state.error,
                color = MaterialTheme.colors.error,
            )
        }
    }
}