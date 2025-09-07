package com.raylabs.mathverse.feature.mychoice.domain.usecase

import com.raylabs.mathverse.feature.mychoice.data.local.Option
import com.raylabs.mathverse.feature.mychoice.data.local.Price
import com.raylabs.mathverse.feature.mychoice.data.local.ProCon
import com.raylabs.mathverse.feature.mychoice.data.local.ProConType
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import com.raylabs.mathverse.feature.mychoice.data.repo.SelectionBundle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ComputeScoresUseCaseTest {

    private fun make(
        prices: Map<Long, Long?>,
        pros: Map<Long, Int>,
        cons: Map<Long, Int>,
        weights: Triple<Float, Float, Float> = Triple(0.5f, 0.3f, 0.2f)
    ): Pair<Selection, SelectionBundle> {
        val options = prices.keys.map { id -> Option(id = id, selectionId = 1, name = "Opt$id") }
        val priceList = prices.flatMap { (id, p) ->
            if (p != null) listOf(
                Price(
                    optionId = id,
                    amount = p
                )
            ) else emptyList()
        }
        val prosList = pros.flatMap { (id, n) ->
            List(n) {
                ProCon(
                    optionId = id,
                    type = ProConType.PRO,
                    text = "p"
                )
            }
        }
        val consList = cons.flatMap { (id, n) ->
            List(n) {
                ProCon(
                    optionId = id,
                    type = ProConType.CON,
                    text = "c"
                )
            }
        }
        val (wP, wPr, wC) = weights
        val sel =
            Selection(id = 1, title = "T", weightPrice = wP, weightPros = wPr, weightCons = wC)
        val bundle = SelectionBundle(options, prosList + consList, priceList)
        return sel to bundle
    }

    @Test
    fun `price normalization prefers cheaper`() {
        val (sel, bundle) = make(
            prices = mapOf(1L to 10_000L, 2L to 20_000L),
            pros = mapOf(1L to 0, 2L to 0),
            cons = mapOf(1L to 0, 2L to 0)
        )
        val res = ComputeScoresUseCase().execute(sel, bundle)
        assertEquals(2, res.rows.size)
        // Opt1 lebih murah → skor total harus >= Opt2
        val first = res.rows.first()
        assertEquals(1L, first.option.id)
        assertTrue(first.finalScore >= res.rows[1].finalScore)
        assertEquals(first.option.id, res.bestOptionId)
    }

    @Test
    fun `equal prices yield priceScore 1 for all`() {
        val (sel, bundle) = make(
            prices = mapOf(1L to 10_000L, 2L to 10_000L, 3L to 10_000L),
            pros = mapOf(1L to 1, 2L to 2, 3L to 0),
            cons = emptyMap()
        )
        val res = ComputeScoresUseCase().execute(sel, bundle)
        // Harga sama → pembeda adalah pros
        assertEquals(2L, res.rows.first().option.id)
    }

    @Test
    fun `weights affect final score`() {
        // Opsi A murah tapi pros sedikit, Opsi B mahal tapi pros banyak.
        val (selCheap, bundle) = make(
            prices = mapOf(1L to 10_000L, 2L to 30_000L),
            pros = mapOf(1L to 1, 2L to 5),
            cons = mapOf(1L to 0, 2L to 0),
            weights = Triple(0.7f, 0.2f, 0.1f) // bobot harga tinggi → A harus menang
        )
        val resCheap = ComputeScoresUseCase().execute(selCheap, bundle)
        assertEquals(1L, resCheap.bestOptionId)

        val (selPros, bundle2) = make(
            prices = mapOf(1L to 10_000L, 2L to 30_000L),
            pros = mapOf(1L to 1, 2L to 5),
            cons = mapOf(1L to 0, 2L to 0),
            weights = Triple(0.2f, 0.7f, 0.1f) // bobot pros tinggi → B harus menang
        )
        val resPros = ComputeScoresUseCase().execute(selPros, bundle2)
        assertEquals(2L, resPros.bestOptionId)
    }

    @Test
    fun `handles missing prices and no proscons`() {
        val (sel, bundle) = make(
            prices = mapOf(1L to null, 2L to null),
            pros = emptyMap(),
            cons = emptyMap()
        )
        val res = ComputeScoresUseCase().execute(sel, bundle)
        // Tanpa harga dan prokon → priceScore default 1; urutan stabil
        assertEquals(2, res.rows.size)
        assertNotNull(res.bestOptionId)
    }
}