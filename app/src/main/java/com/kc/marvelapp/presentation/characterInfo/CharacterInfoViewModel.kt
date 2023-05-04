package com.kc.marvelapp.presentation.characterInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.marvelapp.domain.usecase.getCharacter.GetCharacterUseCase
import com.kc.marvelapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GetCharacterUseCase
): ViewModel() {

    var state by mutableStateOf(CharacterInfoState())

    init{
        val id = savedStateHandle.get<String>(ID)
        if (id != null) {
            getCharacterInfo(id)
        }
    }

    private fun getCharacterInfo(id: String) {
        viewModelScope.launch {
            useCase(id).collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { character ->
                                state = state.copy(character = character)
                            } ?: run {
                                state = state.copy(error = EMPTY_ERROR)
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            state = state.copy(error = result.message)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    companion object {
        private const val ID = "id"
        private const val EMPTY_ERROR = "Result is empty"
    }
}