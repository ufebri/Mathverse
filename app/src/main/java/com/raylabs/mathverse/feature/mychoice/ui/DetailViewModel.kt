package com.raylabs.mathverse.feature.mychoice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository
import com.raylabs.mathverse.feature.mychoice.domain.usecase.ComputeScoresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repo: MyChoiceRepository,
    private val compute: ComputeScoresUseCase,
    private val selectionId: Long
) : ViewModel() {
    private val _ui = MutableStateFlow<UiState>(UiState.Loading)
    val ui: StateFlow<UiState> = _ui

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _ui.value = UiState.Loading
        try {
            val selection = repo.observeSelections().first().first { it.id == selectionId }
            val bundle = repo.getSelectionBundle(selectionId)
            val result = compute.execute(selection, bundle)
            _ui.value = UiState.Data(selection, result)
        } catch (e: Exception) {
            _ui.value = UiState.Error(e.message ?: "Error")
        }
    }

    sealed interface UiState {
        object Loading : UiState
        data class Data(val selection: Selection, val result: ComputeScoresUseCase.Result) : UiState
        data class Error(val msg: String) : UiState
    }
}