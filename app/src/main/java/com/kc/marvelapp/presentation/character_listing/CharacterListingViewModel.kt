package com.kc.marvelapp.presentation.character_listing

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.domain.use_case.get_characters.GetCharactersUseCase
import com.kc.marvelapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListingViewModel @Inject constructor(
    private val useCase: GetCharactersUseCase
): ViewModel() {

    var state by mutableStateOf(CharacterListingState())

    init {
        getCharacterListings()
    }

    private fun getCharacterListings(
    ) {
        viewModelScope.launch {
            useCase().collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    characters = listings
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            state = state.copy(error = result.message)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}