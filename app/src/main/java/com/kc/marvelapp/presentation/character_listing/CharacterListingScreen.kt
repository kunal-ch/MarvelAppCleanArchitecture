package com.kc.marvelapp.presentation.character_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kc.marvelapp.R
import com.kc.marvelapp.navigation.Screen
import com.kc.marvelapp.ui.theme.DarkBlue
import com.kc.marvelapp.ui.theme.TextWhite

/**
 * Shows the list for characters
 */
@Composable
fun CharacterListingScreen(
    navController: NavController,
    viewModel: CharacterListingViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (state.error == null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                backgroundColor = DarkBlue,
                contentColor = TextWhite,
                elevation = 2.dp
            )
            Divider()
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
                                navController.navigate(Screen.CharacterInfoScreen.withArgs(character.id.toString()))
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
    Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
    ) {
        if(state.isLoading) {
            CircularProgressIndicator()
        } else if(state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error
            )
        }
    }
}