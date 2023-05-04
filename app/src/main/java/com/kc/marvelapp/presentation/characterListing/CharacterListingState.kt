package com.kc.marvelapp.presentation.characterListing

import com.kc.marvelapp.domain.models.ComicCharacter

/**
 * State for character listing screen
 */
data class CharacterListingState(
    val characters: List<ComicCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
