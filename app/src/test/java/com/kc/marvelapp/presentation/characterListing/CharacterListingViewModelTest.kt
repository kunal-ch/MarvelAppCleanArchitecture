package com.kc.marvelapp.presentation.characterListing

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.data.mapper.toAllCharactersResponse
import com.kc.marvelapp.data.service.FileUtils
import com.kc.marvelapp.data.service.dto.AllCharactersResponseDto
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.usecase.getCharacters.GetCharactersUseCase
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
class CharacterListingViewModelTest {
    private var charactersUseCase = mockk<GetCharactersUseCase>(relaxed = true)
    private lateinit var characterListingViewModel: CharacterListingViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupCharacterListingViewModel() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        characterListingViewModel = CharacterListingViewModel(charactersUseCase)
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

        coEvery { charactersUseCase.getCharacterList()} returns flow

        characterListingViewModel.getCharacterListings()
        val stateSuccess = characterListingViewModel.state.characters
        assertThat(stateSuccess).isNotEmpty()
    }

    @Test
    fun fetchCharacterListLoadingTest() = runTest {
        val flow = flow {
            emit(Resource.Loading(true))
        }

        coEvery { charactersUseCase.getCharacterList()} returns flow

        characterListingViewModel.getCharacterListings()
        val stateLoading = characterListingViewModel.state.isLoading
        assertThat(stateLoading).isNotNull()
    }

    @Test
    fun fetchCharacterListErrorTest() = runTest {
        val flow = flow {
            emit(Resource.Error("Something went wrong"))
        }

        coEvery { charactersUseCase.getCharacterList()} returns flow

        characterListingViewModel.getCharacterListings()
        val stateError = characterListingViewModel.state.error
        assertThat(stateError).isEqualTo("Something went wrong")
    }

    @Test
    fun fetchCharacterListSizeTest() = runTest {
        val flow = flow {
            emit(Resource.Success(getCharacterListResponse()))
        }

        coEvery { charactersUseCase.getCharacterList()} returns flow

        characterListingViewModel.getCharacterListings()
        val stateSuccess = characterListingViewModel.state.characters
        assertThat(stateSuccess).hasSize(20)
    }
}