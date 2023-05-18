package com.kc.marvelapp.domain.usecase.getCharacters

import com.kc.marvelapp.domain.repository.MarvelRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    suspend operator fun invoke() = repository.getCharacterListings()
}