package com.raylabs.mathverse.feature.mychoice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repo: MyChoiceRepository
) : ViewModel() {
    private val _ui = MutableStateFlow<UiState>(UiState.Loading)
    val ui: StateFlow<UiState> = _ui

    init {
        viewModelScope.launch {
            repo.observeSelections().collect { selections ->
                _ui.value = UiState.Data(selections)
            }
        }
    }

    sealed interface UiState {
        object Loading : UiState
        data class Data(val selections: List<Selection>) : UiState
    }
}