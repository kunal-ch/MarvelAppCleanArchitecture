package com.kc.marvelapp.presentation.characterInfo

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.kc.marvelapp.MainCoroutineRule
import com.kc.marvelapp.data.service.FileUtils
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.usecase.getCharacter.GetCharacterUseCase
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
class CharacterInfoViewModelTest {
    private var characterUseCase = mockk<GetCharacterUseCase>(relaxed = true)
    private lateinit var characterInfoViewModel: CharacterInfoViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val savedStateHandle = SavedStateHandle()
        savedStateHandle["id"] = id
        characterInfoViewModel = CharacterInfoViewModel(savedStateHandle, characterUseCase)
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

        coEvery { characterUseCase.getCharacterInfo(any())} returns flow

        characterInfoViewModel.getCharacterInfo(id)
        val stateSuccess = characterInfoViewModel.state.character
        assertThat(stateSuccess).isNotNull()
    }

    @Test
    fun fetchCharacterInfoLoadingTest() = runTest {
        val flow = flow {
            emit(Resource.Loading(true))
        }

        coEvery { characterUseCase.getCharacterInfo(id)} returns(flow)

        characterInfoViewModel.getCharacterInfo(id)
        val stateLoading = characterInfoViewModel.state.isLoading
        assertThat(stateLoading).isNotNull()
    }

    @Test
    fun fetchCharacterInfoErrorTest() = runTest {
        val flow = flow {
            emit(Resource.Error("Something went wrong"))
        }

        coEvery { characterUseCase.getCharacterInfo(id)} returns(flow)

        characterInfoViewModel.getCharacterInfo(id)
        val stateError = characterInfoViewModel.state.error
        assertThat(stateError).isEqualTo("Something went wrong")
    }

    @Test
    fun fetchCharacterInfoSuccessValidateTest() = runTest {
        val flow = flow {
            emit(Resource.Success(getCharacterResponse()))
        }

        coEvery { characterUseCase.getCharacterInfo(id)} returns(flow)

        characterInfoViewModel.getCharacterInfo(id)
        val stateSuccess = characterInfoViewModel.state.character
        assertThat(stateSuccess).isNotNull()
        assertThat(stateSuccess?.id).isEqualTo(1011334)
        assertThat(stateSuccess?.modified).isEqualTo("2014-04-29T14:18:17-0400")
        assertThat(stateSuccess?.description).isEqualTo("")
        assertThat(stateSuccess?.name).isEqualTo("3-D Man")
        assertThat(stateSuccess?.resourceURI).isEqualTo("http://gateway.marvel.com/v1/public/characters/1011334")
        assertThat(stateSuccess?.thumbnail?.extension).isEqualTo("jpg")
        assertThat(stateSuccess?.thumbnail?.path).isEqualTo("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784")
    }
}

