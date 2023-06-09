package com.kc.marvelapp.domain.usecase.getCharacter

import com.kc.marvelapp.domain.repository.MarvelRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    suspend operator fun invoke(id: String) = repository.getCharacterInfo(id)
}