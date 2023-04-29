package com.kc.marvelapp.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.data.service.MarvelApi
import com.kc.marvelapp.data.service.dto.*
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class MarvelRepositoryImplTest {

    private lateinit var repository: MarvelRepository
    private lateinit var api: MarvelApi

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


    @Before
    fun setUp() {
       api = mockk(relaxed = true){
           coEvery { getAllCharacters(any()) } returns Response.success(allCharactersResponse)
           coEvery { getCharacter(any()) } returns Response.success(allCharactersResponse)
       }
       repository = MarvelRepositoryImpl (
           api = api
       )
    }

    @Test
    fun `Test Get All Characters Response`() = runBlocking {
        repository.getCharacterListings().test {
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val remoteListings = awaitItem()
            assertThat(remoteListings is Resource.Success).isTrue()

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }

    @Test
    fun `Test Get Character Response`() = runBlocking {
        repository.getCharacterInfo("1").test {
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val remoteListings = awaitItem()
            assertThat(remoteListings is Resource.Success).isTrue()

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }
}