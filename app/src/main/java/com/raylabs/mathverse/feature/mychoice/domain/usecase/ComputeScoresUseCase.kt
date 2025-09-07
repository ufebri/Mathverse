package com.raylabs.mathverse.feature.mychoice.domain.usecase

import com.raylabs.mathverse.feature.mychoice.data.local.Option
import com.raylabs.mathverse.feature.mychoice.data.local.ProConType
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import com.raylabs.mathverse.feature.mychoice.data.repo.SelectionBundle

class ComputeScoresUseCase {
    data class Result(
        val rows: List<Row>,
        val bestOptionId: Long?
    ) {
        data class Row(
            val option: Option,
            val priceMin: Long?,
            val prosCount: Int,
            val consCount: Int,
            val priceScore: Float,
            val prosScore: Float,
            val consScore: Float,
            val finalScore: Float
        )
    }

    fun execute(sel: Selection, bundle: SelectionBundle): Result {
        val groupedPrice = bundle.prices.groupBy { it.optionId }
            .mapValues { it.value.minOfOrNull { p -> p.amount } }
        val prosCount = bundle.proscons.filter { it.type == ProConType.PRO }.groupBy { it.optionId }
            .mapValues { it.value.size }
        val consCount = bundle.proscons.filter { it.type == ProConType.CON }.groupBy { it.optionId }
            .mapValues { it.value.size }

        val priceValues = groupedPrice.values.filterNotNull()
        val minP = priceValues.minOrNull()?.toFloat()
        val maxP = priceValues.maxOrNull()?.toFloat()
        val denom = ((maxP ?: 0f) - (minP ?: 0f)).takeIf { it != 0f }

        val maxPros = (prosCount.values.maxOrNull() ?: 1).toFloat()
        val maxCons = (consCount.values.maxOrNull() ?: 1).toFloat()

        val rows = bundle.options.map { opt ->
            val pMin = groupedPrice[opt.id]
            val pScore =
                if (pMin == null || minP == null || denom == null) 1f else 1f - ((pMin - minP) / denom)
            val pr = (prosCount[opt.id] ?: 0).toFloat()
            val co = (consCount[opt.id] ?: 0).toFloat()
            val prosS = if (maxPros == 0f) 0f else pr / maxPros
            val consS = if (maxCons == 0f) 0f else co / maxCons
            val final = sel.weightPrice * pScore + sel.weightPros * prosS - sel.weightCons * consS
            Result.Row(opt, pMin, pr.toInt(), co.toInt(), pScore, prosS, consS, final)
        }.sortedByDescending { it.finalScore }

        return Result(rows, rows.firstOrNull()?.option?.id)
    }
}