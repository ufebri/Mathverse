package com.raylabs.mathverse.feature.mychoice.di

import android.content.Context
import androidx.room.Room
import com.raylabs.mathverse.feature.mychoice.data.local.AppDb
import com.raylabs.mathverse.feature.mychoice.data.repo.MyChoiceRepository
import com.raylabs.mathverse.feature.mychoice.domain.usecase.ComputeScoresUseCase

class ServiceLocator(appContext: Context) {
    private val db: AppDb by lazy {
        Room.databaseBuilder(appContext, AppDb::class.java, "mychoice.db").build()
    }
    val repo: MyChoiceRepository by lazy { MyChoiceRepository(db) }
    val compute: ComputeScoresUseCase by lazy { ComputeScoresUseCase() }
}