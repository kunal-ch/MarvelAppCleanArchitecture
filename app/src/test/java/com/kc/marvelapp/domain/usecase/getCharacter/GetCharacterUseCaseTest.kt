package com.kc.marvelapp.domain.usecase.getCharacter

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.data.service.FileUtils
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Constants.id
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
class GetCharacterUseCaseTest {
    private var repository = mockk<MarvelRepository>(relaxed = true)
    private var characterUseCase = GetCharacterUseCase(repository)

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun getCharacterResponse(): ComicCharacter {
        val response = FileUtils.readTestResourceFile("character_detail.json")
        return Gson().fromJson(response, ComicCharacter::class.java)
    }

    @Test
    fun fetchCharacterInfoSuccessTest() = runTest {
        val flow = flow {
            emit(Resource.Success(getCharacterResponse()))
        }

        coEvery { repository.getCharacterInfo(any()) } returns flow

        characterUseCase.getCharacterInfo(id).collect {
            when (it) {
                is Resource.Success -> {
                    assertThat(it.data.id).isEqualTo(1011334)
                }
                else -> {
                    assert(false)
                }
            }
        }
    }

    @Test
    fun fetchCharacterInfoLoadingTest() = runTest {
        val flow = flow {
            emit(Resource.Loading(true))
        }

        coEvery { repository.getCharacterInfo(any()) } returns flow

        characterUseCase.getCharacterInfo(id).collect {
            when (it) {
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
    fun fetchCharacterInfoErrorTest() = runTest {
        val flow = flow {
            emit(Resource.Error("Something went wrong"))
        }

        coEvery { repository.getCharacterInfo(any()) } returns flow

        characterUseCase.getCharacterInfo(id).collect {
            when (it) {
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
