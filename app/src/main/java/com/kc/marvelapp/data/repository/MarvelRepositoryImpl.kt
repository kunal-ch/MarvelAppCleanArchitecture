package com.kc.marvelapp.data.repository

import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.MarvelApi
import com.kc.marvelapp.di.IoDispatcher
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository Implementation
 */
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    ): MarvelRepository {

    override suspend fun getCharacterListings(
    ): Flow<Resource<List<ComicCharacter>>> {
        return flow<Resource<List<ComicCharacter>>> {
            emit(Resource.Loading(true))
            val remoteListings = getList(api)
            remoteListings?.let { listings ->
                emit(Resource.Success(data = listings))
                emit(Resource.Loading(false))
            } ?: run {
                emit(Resource.Error("Couldn't load data"))
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getCharacterInfo(
        id: String,
    ): Flow<Resource<ComicCharacter>> {
        return flow<Resource<ComicCharacter>> {
            emit(Resource.Loading(true))
            val remoteListings = getList(api)
            remoteListings?.let { remoteListings
                if (remoteListings.isNotEmpty()) {
                    emit(Resource.Success(data = remoteListings[0]))
                }
            } ?: run {
                emit(Resource.Error("Couldn't load data"))
            }
            emit(Resource.Loading(false))
        }.flowOn(dispatcher)
    }

    private suspend fun getList(api : MarvelApi) : List<ComicCharacter>? {
        val remoteListings = try {
            val response = api.getAllCharacters()
            if (response.isSuccessful) {
                response.body()?.toAllCharactersResponse()?.data?.characters
            } else {
                listOf()
            }
        }  catch (e: HttpException) {
            e.printStackTrace()
            null
        }
        catch (e: IOException) {
            e.printStackTrace()
            null
        }
        return remoteListings
    }
}