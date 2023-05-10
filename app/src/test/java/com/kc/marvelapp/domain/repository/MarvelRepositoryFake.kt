package com.kc.marvelapp.domain.repository

import com.google.gson.Gson
import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.FileUtils
import com.kc.marvelapp.data.service.dto.*
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarvelRepositoryFake: MarvelRepository {
    override suspend fun getCharacterListings(): Flow<Resource<List<ComicCharacter>>> {
        return flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(getCharacterResponse()))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCharacterInfo(id: String): Flow<Resource<ComicCharacter>> {
        return flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(getCharacterResponse()[0]))
            emit(Resource.Loading(false))
        }
    }

    private fun getCharacterResponse() : List<ComicCharacter>{
        val response = FileUtils.readTestResourceFile("success_response.json")
        val characters = Gson().fromJson(response, AllCharactersResponseDto::class.java)
        return characters.toAllCharactersResponse().data.characters
    }
}