package com.kc.marvelapp.data.repository

import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.MarvelApi
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.Dispatchers
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
    private val api: MarvelApi
    ): MarvelRepository {

    override suspend fun getCharacterListings(
    ): Flow<Resource<List<ComicCharacter>>> {
        return flow<Resource<List<ComicCharacter>>> {
            emit(Resource.Loading(true))

            val remoteListings = try {
                val response = api.getAllCharacters()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        resultResponse.toAllCharactersResponse().data.characters
                    }
                } else {
                    listOf()
                }
            }  catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                emit(
                    Resource.Success(
                        data = listings
                    )
                )
                emit(Resource.Loading(false))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCharacterInfo(
        id: String,
    ): Flow<Resource<ComicCharacter>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteListings = try {
                val response = api.getCharacter(id)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        resultResponse.toAllCharactersResponse().data.characters
                    }
                } else {
                    listOf()
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let { remoteListings
                if (remoteListings.isNotEmpty()) {
                    emit(
                        Resource.Success(
                            data = remoteListings[0]
                        )
                    )
                }
            }
            emit(Resource.Loading(false))
            return@flow
        }.flowOn(Dispatchers.IO)
    }
}