package com.raylabs.mathverse.feature.mychoice

import android.content.Context
import androidx.room.Room
import com.raylabs.mathverse.feature.mychoice.data.local.AppDb
import com.raylabs.mathverse.feature.mychoice.data.local.Option
import com.raylabs.mathverse.feature.mychoice.data.local.Price
import com.raylabs.mathverse.feature.mychoice.data.local.ProCon
import com.raylabs.mathverse.feature.mychoice.data.local.ProConType
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import kotlinx.coroutines.flow.firstOrNull

object MyChoiceSeeder {
    fun seedIfNeeded(context: Context) {
        val db = Room.databaseBuilder(context, AppDb::class.java, "mychoice.db").build()
        kotlinx.coroutines.runBlocking {
            val existing = db.selectionDao().observeSelections().firstOrNull() ?: emptyList()
            if (existing.isNotEmpty()) return@runBlocking

            val selId = db.selectionDao().insert(Selection(title = "Laptop Kerja 2025"))

            val optA = db.optionDao().insert(Option(selectionId = selId, name = "Dell XPS"))
            val optB = db.optionDao().insert(Option(selectionId = selId, name = "MacBook Air"))
            val optC = db.optionDao().insert(Option(selectionId = selId, name = "Lenovo ThinkPad"))

            db.priceDao().insert(Price(optionId = optA, amount = 20_000_000))
            db.priceDao().insert(Price(optionId = optB, amount = 25_000_000))
            db.priceDao().insert(Price(optionId = optC, amount = 18_000_000))

            db.proConDao()
                .insert(ProCon(optionId = optA, type = ProConType.PRO, text = "Layar tajam"))
            db.proConDao()
                .insert(ProCon(optionId = optA, type = ProConType.CON, text = "Lebih berat"))
            db.proConDao().insert(
                ProCon(
                    optionId = optB,
                    type = ProConType.PRO,
                    text = "Ringan & baterai awet"
                )
            )
            db.proConDao()
                .insert(ProCon(optionId = optB, type = ProConType.CON, text = "Lebih mahal"))
            db.proConDao()
                .insert(ProCon(optionId = optC, type = ProConType.PRO, text = "Keyboard enak"))
            db.proConDao()
                .insert(ProCon(optionId = optC, type = ProConType.CON, text = "Desain konservatif"))
        }
    }
}