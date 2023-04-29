package com.kc.marvelapp.domain.use_case.get_character

import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: MarvelRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<ComicCharacter>> {
        return repository.getCharacterInfo(id)
    }
}