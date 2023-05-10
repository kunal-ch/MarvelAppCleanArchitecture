package com.kc.marvelapp.presentation.characterListing

import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.domain.repository.MarvelRepositoryFake
import com.kc.marvelapp.domain.usecase.getCharacters.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterListingViewModelTest {

    private lateinit var characterListingViewModel: CharacterListingViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var fakeRepository: MarvelRepositoryFake

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupCharacterListingViewModel() {
        fakeRepository = MarvelRepositoryFake()
        getCharactersUseCase = GetCharactersUseCase(fakeRepository)
        characterListingViewModel = CharacterListingViewModel(getCharactersUseCase)
    }

    @Test
    fun `check loading valid`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        var isLoading: Boolean? = true
        val job = launch {
            isLoading = characterListingViewModel.state.isLoading
        }
        assertThat(isLoading).isTrue()
        advanceUntilIdle()
        assertThat(isLoading).isFalse()
        job.cancel()
    }

    @Test
    fun `check result character list size valid greater than 0`() = runTest {
        val uiState = characterListingViewModel.state.characters
        assertThat(uiState).hasSize(20)
    }

    @Test
    fun `check error should not be null`() = runTest {
        val uiState = characterListingViewModel.state.error
        assertThat(uiState).isNull()
    }
}