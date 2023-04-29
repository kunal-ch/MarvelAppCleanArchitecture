package com.kc.marvelapp.data.service

import com.kc.marvelapp.BuildConfig
import com.kc.marvelapp.data.service.dto.AllCharactersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("hash") hash : String = BuildConfig.HASH,
        @Query("ts") ts: Int = 1,
    ) : Response<AllCharactersResponseDto>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
        @Query("hash") hash : String = BuildConfig.HASH,
        @Query("ts") ts: Int = 1,
    ) : Response<AllCharactersResponseDto>
}