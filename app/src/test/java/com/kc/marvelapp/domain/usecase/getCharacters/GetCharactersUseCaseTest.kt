package com.kc.marvelapp.domain.usecase.getCharacters

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.FileUtils
import com.kc.marvelapp.data.service.dto.AllCharactersResponseDto
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {
    private var repository = mockk<MarvelRepository>(relaxed = true)
    private var charactersUseCase = GetCharactersUseCase(repository)

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun getCharacterListResponse(): List<ComicCharacter> {
        val response = FileUtils.readTestResourceFile("character_list.json")
        return Gson().fromJson(response, AllCharactersResponseDto::class.java).toAllCharactersResponse().data.characters
    }

    @Test
    fun fetchCharacterListSuccessTest() = runTest {
        val flow = flow {
            emit(Resource.Success(getCharacterListResponse()))
        }

        coEvery { repository.getCharacterListings()} returns flow

        charactersUseCase.getCharacterList().collect {
            when(it){
                is Resource.Success -> {
                    assertThat(it.data).isNotEmpty()
                }
                else -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun fetchCharacterListLoadingTest() = runTest {
        val flow = flow {
            emit(Resource.Loading(true))
        }

        coEvery { repository.getCharacterListings()} returns flow

        charactersUseCase.getCharacterList().collect {
            when(it){
                is Resource.Loading -> {
                    assertThat(it.isLoading).isEqualTo(true)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun fetchCharacterListErrorTest() = runTest {
        val flow = flow {
            emit(Resource.Error("Something went wrong"))
        }

        coEvery { repository.getCharacterListings()} returns flow

        charactersUseCase.getCharacterList().collect {
            when(it){
                is Resource.Error -> {
                    assertThat(it.message).isEqualTo("Something went wrong")
                }
                else -> {
                    assert(false)
                }
            }
        }
    }
}