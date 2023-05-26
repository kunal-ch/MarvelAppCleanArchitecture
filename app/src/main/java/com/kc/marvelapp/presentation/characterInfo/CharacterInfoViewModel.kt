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
    savedStateHandle: SavedStateHandle,
    private val useCase: GetCharacterUseCase
): ViewModel() {

    var state by mutableStateOf(CharacterInfoState())

    init{
        val id = savedStateHandle.get<String>(ID)
        if (id != null) {
            getCharacterInfo(id)
        }
    }

    fun getCharacterInfo(id: String) {
        viewModelScope.launch {
            useCase.getCharacterInfo(id).collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            state = state.copy(character = result.data)
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
    }
}