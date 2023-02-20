package com.example.amphibians.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.Amphibian
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.data.NetworkAmphibiansRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
    object Loading : AmphibiansUiState
    object Error : AmphibiansUiState
}

class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {
    private var _uiState: MutableStateFlow<AmphibiansUiState> =
        MutableStateFlow(AmphibiansUiState.Loading)
    val uiState: StateFlow<AmphibiansUiState> = _uiState.asStateFlow()

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            _uiState.update {
                try {
                    AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
                }
                catch (e: IOException) {
                    AmphibiansUiState.Error
                }
                catch (e: HttpException) {
                    AmphibiansUiState.Error
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository)
            }
        }
    }
}