package com.raylabs.mathverse.feature.mychoice.data.repo

import com.raylabs.mathverse.feature.mychoice.data.local.AppDb
import com.raylabs.mathverse.feature.mychoice.data.local.Option
import com.raylabs.mathverse.feature.mychoice.data.local.Price
import com.raylabs.mathverse.feature.mychoice.data.local.ProCon
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

data class SelectionBundle(
    val options: List<Option>,
    val proscons: List<ProCon>,
    val prices: List<Price>
)

class MyChoiceRepository(
    private val db: AppDb,
    private val io: CoroutineDispatcher = Dispatchers.IO
) {
    fun observeSelections(): Flow<List<Selection>> = db.selectionDao().observeSelections()

    suspend fun getSelectionBundle(selectionId: Long): SelectionBundle = withContext(io) {
        val options = db.optionDao().getOptions(selectionId)
        val ids = options.map { it.id }
        val proscons = db.proConDao().getByOptionIds(ids)
        val prices = db.priceDao().getByOptionIds(ids)
        SelectionBundle(options, proscons, prices)
    }
}