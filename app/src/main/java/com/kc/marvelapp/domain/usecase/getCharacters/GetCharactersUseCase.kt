package com.kc.marvelapp.domain.usecase.getCharacters

import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    suspend operator fun invoke() = repository.getCharacterListings()
}