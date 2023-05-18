package com.kc.marvelapp.presentation.characterListing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kc.marvelapp.R
import com.kc.marvelapp.navigation.Screen
import com.kc.marvelapp.presentation.components.CustomText
import com.kc.marvelapp.presentation.components.Toolbar

/**
 * Shows the list for characters
 */
@Composable
fun CharacterListingScreen(
    navController: NavController,
    viewModel: CharacterListingViewModel = hiltViewModel(),
    vmState: CharacterListingState = viewModel.state,
) {
    val state = vmState
    if (state.error == null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Toolbar(title = stringResource(id = R.string.app_name))
            Divider()
            if (state.characters.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.characters.size) { i ->
                        val character = state.characters[i]
                        CharacterItem(
                            character = character,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        Screen.CharacterInfoScreen.withArgs(
                                            character.id.toString()
                                        )
                                    )
                                }
                                .padding(16.dp)
                        )
                        if (i < state.characters.size) {
                            Divider(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp
                                )
                            )
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
                color = MaterialTheme.colors.error
            )
        }
    }
}