// DetailViewModelFactory.kt
package com.raylabs.mathverse.feature.mychoice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository
import com.raylabs.mathverse.feature.mychoice.domain.usecase.ComputeScoresUseCase

class DetailViewModelFactory(
    private val repo: MyChoiceRepository,
    private val compute: ComputeScoresUseCase,
    private val selectionId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repo, compute, selectionId) as T
    }
}