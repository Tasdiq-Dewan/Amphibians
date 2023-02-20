package com.example.amphibians.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.amphibians.data.Amphibian
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState
    object Loading : AmphibiansUiState
    object Error : AmphibiansUiState
}

class AmphibiansViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<AmphibiansUiState> =
        MutableStateFlow(AmphibiansUiState.Loading)
    val uiState: StateFlow<AmphibiansUiState> = _uiState.asStateFlow()
}