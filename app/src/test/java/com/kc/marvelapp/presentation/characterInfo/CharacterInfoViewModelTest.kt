package com.kc.marvelapp.presentation.characterInfo

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.models.Comics
import com.kc.marvelapp.domain.models.Thumbnail
import com.kc.marvelapp.domain.repository.MarvelRepositoryFake
import com.kc.marvelapp.domain.usecase.getCharacter.GetCharacterUseCase
import com.kc.marvelapp.util.Constants
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
    private lateinit var savedStateHandle: SavedStateHandle

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupCharacterListingViewModel() {
        fakeRepository = MarvelRepositoryFake()
        getCharacterUseCase = GetCharacterUseCase(fakeRepository)
        savedStateHandle = SavedStateHandle()
        savedStateHandle["id"] = Constants.fakeId
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
        val comicCharacter = getComicCharacter()
        assertThat(uiState?.id).isEqualTo(comicCharacter.id)
        assertThat(uiState?.modified).isEqualTo(comicCharacter.modified)
        assertThat(uiState?.description).isEqualTo(comicCharacter.description)
        assertThat(uiState?.name).isEqualTo(comicCharacter.name)
        assertThat(uiState?.resourceURI).isEqualTo(comicCharacter.resourceURI)
        assertThat(uiState?.thumbnail?.extension).isEqualTo(comicCharacter.thumbnail.extension)
        assertThat(uiState?.thumbnail?.path).isEqualTo(comicCharacter.thumbnail.path)
    }

    @Test
    fun `check error should not be null`() = runTest {
        val uiState = characterInfoViewModel.state.error
        assertThat(uiState).isNull()
    }

    private fun getComicCharacter(): ComicCharacter = ComicCharacter(
            id = 1011334,
            modified = "2014-04-29T14:18:17-0400",
            description = "",
            name = "3-D Man",
            resourceURI = "http://gateway.marvel.com/v1/public/characters/1011334",
            thumbnail = Thumbnail("jpg", "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"),
            comics = Comics(
                available = 1,
                collectionURI = "",
                returned = 1,
                items = listOf()
            )
        )
    }
