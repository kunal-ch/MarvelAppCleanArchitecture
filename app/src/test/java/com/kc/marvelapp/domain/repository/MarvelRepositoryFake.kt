package com.kc.marvelapp.domain.repository

import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.dto.*
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class MarvelRepositoryFake: MarvelRepository {

    private val comicItemMock = mockk<ComicsDto>()
    private val thumbailMock = mockk<ThumbnailDto>()

    private val characterListings = (1..100).map {
        ResultDto(
            id = it,
            modified = LocalDateTime.now().toString(),
            description = "description$it",
            name = "name$it",
            resourceURI = "resourceURI$it",
            thumbnailDto = thumbailMock,
            comicsDto = comicItemMock,
            eventsDto = null,
            seriesDto = null,
            storiesDto = null,
            urlDtos = null
        )
    }

    private val mData = DataDto(
        count = 1,
        limit = 1,
        offset = 1,
        total = 1,
        resultDtos = characterListings
    )

    private val allCharactersResponse = AllCharactersResponseDto(
        code = 1,
        status = "status",
        dataDto = mData,
        attributionHTML = "",
        attributionText = "",
        etag = "",
        copyright = ""
    )

    override suspend fun getCharacterListings(): Flow<Resource<List<ComicCharacter>>> {
        return flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(allCharactersResponse.toAllCharactersResponse().data.characters))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCharacterInfo(id: String): Flow<Resource<ComicCharacter>> {
        return flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(allCharactersResponse.toAllCharactersResponse().data.characters.get(0)))
            emit(Resource.Loading(false))
        }
    }
}