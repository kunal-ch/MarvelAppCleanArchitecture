package com.kc.marvelapp.data.repository

import android.util.Log
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
import javax.inject.Singleton

/**
 * Repository Implementation
 */
@Singleton
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi
    ): MarvelRepository {

    private val TAG = "MarvelRepositoryImpl"

    override suspend fun getCharacterListings(
    ): Flow<Resource<List<ComicCharacter>>> {
        return flow<Resource<List<ComicCharacter>>> {
            emit(Resource.Loading(true))

            val remoteListings = try {
                val response = api.getAllCharacters()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        Log.d(TAG, "Fetch all characters successful")
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

            Log.d(TAG, "Character List Count : ${remoteListings?.size}")
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
                        Log.d(TAG, "Fetch Character detail successful")
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