package com.kc.marvelapp.presentation.characterInfo

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.domain.repository.MarvelRepositoryFake
import com.kc.marvelapp.domain.usecase.getCharacter.GetCharacterUseCase
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
class CharacterInfoViewModelTest {

    private lateinit var characterInfoViewModel: CharacterInfoViewModel
    private lateinit var getCharacterUseCase: GetCharacterUseCase
    private lateinit var fakeRepository: MarvelRepositoryFake

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupCharacterListingViewModel() {
        fakeRepository = MarvelRepositoryFake()
        getCharacterUseCase = GetCharacterUseCase(fakeRepository)
        val savedStateHandle = SavedStateHandle()
        savedStateHandle.set("id","1011334")
        characterInfoViewModel = CharacterInfoViewModel(savedStateHandle, getCharacterUseCase)
    }

    @Test
    fun `check loading valid`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        var isLoading: Boolean? = true
        val job = launch {
            isLoading = characterInfoViewModel.state.isLoading
        }
        assertThat(isLoading).isTrue()
        advanceUntilIdle()
        assertThat(isLoading).isFalse()
        job.cancel()
    }

    @Test
    fun `check result character valid`() = runTest {
        val uiState = characterInfoViewModel.state.character
        assertThat(uiState).isNotNull()
    }

    @Test
    fun `check error should not be null`() = runTest {
        val uiState = characterInfoViewModel.state.error
        assertThat(uiState).isNull()
    }
}