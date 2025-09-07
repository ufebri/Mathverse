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
import kotlinx.coroutines.runBlocking

object MyChoiceSeeder {
    fun seedIfNeeded(context: Context) {
        val db = Room.databaseBuilder(context, AppDb::class.java, "mychoice.db").build()
        runBlocking {
            val existing = db.selectionDao().observeSelections().firstOrNull() ?: emptyList()
            val existingTitles = existing.map { it.title }.toSet()

            if (!existingTitles.contains("Laptop Kerja 2025")) {
                // --- Laptop Kerja 2025 ---
                val selId = db.selectionDao().insert(Selection(title = "Laptop Kerja 2025"))

                val optA = db.optionDao().insert(Option(selectionId = selId, name = "Dell XPS"))
                val optB = db.optionDao().insert(Option(selectionId = selId, name = "MacBook Air"))
                val optC = db.optionDao().insert(Option(selectionId = selId, name = "Lenovo ThinkPad"))

                db.priceDao().insert(Price(optionId = optA, amount = 20_000_000))
                db.priceDao().insert(Price(optionId = optB, amount = 25_000_000))
                db.priceDao().insert(Price(optionId = optC, amount = 18_000_000))

                db.proConDao().insert(ProCon(optionId = optA, type = ProConType.PRO, text = "Layar tajam"))
                db.proConDao().insert(ProCon(optionId = optA, type = ProConType.CON, text = "Lebih berat"))
                db.proConDao().insert(ProCon(optionId = optB, type = ProConType.PRO, text = "Ringan & baterai awet"))
                db.proConDao().insert(ProCon(optionId = optB, type = ProConType.CON, text = "Lebih mahal"))
                db.proConDao().insert(ProCon(optionId = optC, type = ProConType.PRO, text = "Keyboard enak"))
                db.proConDao().insert(ProCon(optionId = optC, type = ProConType.CON, text = "Desain konservatif"))
            }

            if (!existingTitles.contains("Cloud Provider 2024")) {
                // --- Cloud Provider ---
                val selCloud = db.selectionDao().insert(Selection(title = "Cloud Provider 2024"))
                val optAWS = db.optionDao().insert(Option(selectionId = selCloud, name = "AWS"))
                val optGCP = db.optionDao().insert(Option(selectionId = selCloud, name = "Google Cloud"))
                val optAzure = db.optionDao().insert(Option(selectionId = selCloud, name = "Azure"))

                db.priceDao().insert(Price(optionId = optAWS, amount = 5_000_000))
                db.priceDao().insert(Price(optionId = optGCP, amount = 4_500_000))
                db.priceDao().insert(Price(optionId = optAzure, amount = 4_800_000))

                db.proConDao().insert(ProCon(optionId = optAWS, type = ProConType.PRO, text = "Layanan terlengkap"))
                db.proConDao().insert(ProCon(optionId = optAWS, type = ProConType.CON, text = "UI kompleks"))
                db.proConDao().insert(ProCon(optionId = optAWS, type = ProConType.PRO, text = "Banyak region"))
                db.proConDao().insert(ProCon(optionId = optGCP, type = ProConType.PRO, text = "Integrasi AI bagus"))
                db.proConDao().insert(ProCon(optionId = optGCP, type = ProConType.CON, text = "Pilihan layanan lebih sedikit"))
                db.proConDao().insert(ProCon(optionId = optGCP, type = ProConType.PRO, text = "UI mudah dipahami"))
                db.proConDao().insert(ProCon(optionId = optAzure, type = ProConType.PRO, text = "Integrasi dengan Microsoft"))
                db.proConDao().insert(ProCon(optionId = optAzure, type = ProConType.CON, text = "Dokumentasi kadang membingungkan"))
                db.proConDao().insert(ProCon(optionId = optAzure, type = ProConType.PRO, text = "Banyak fitur enterprise"))
            }

            if (!existingTitles.contains("Renovasi Rumah: Pilihan Kontraktor")) {
                // --- Renovasi Rumah ---
                val selRenov = db.selectionDao().insert(Selection(title = "Renovasi Rumah: Pilihan Kontraktor"))
                val optKontraktorA = db.optionDao().insert(Option(selectionId = selRenov, name = "Kontraktor Mandiri"))
                val optKontraktorB = db.optionDao().insert(Option(selectionId = selRenov, name = "Jasa Renovasi Online"))
                val optKontraktorC = db.optionDao().insert(Option(selectionId = selRenov, name = "Rekomendasi Teman"))

                db.priceDao().insert(Price(optionId = optKontraktorA, amount = 100_000_000))
                db.priceDao().insert(Price(optionId = optKontraktorB, amount = 120_000_000))
                db.priceDao().insert(Price(optionId = optKontraktorC, amount = 110_000_000))

                db.proConDao().insert(ProCon(optionId = optKontraktorA, type = ProConType.PRO, text = "Kontrol penuh desain"))
                db.proConDao().insert(ProCon(optionId = optKontraktorA, type = ProConType.CON, text = "Perlu waktu supervisi lebih"))
                db.proConDao().insert(ProCon(optionId = optKontraktorB, type = ProConType.PRO, text = "Praktis & transparan"))
                db.proConDao().insert(ProCon(optionId = optKontraktorB, type = ProConType.CON, text = "Biaya lebih tinggi"))
                db.proConDao().insert(ProCon(optionId = optKontraktorB, type = ProConType.PRO, text = "Garansi pekerjaan"))
                db.proConDao().insert(ProCon(optionId = optKontraktorC, type = ProConType.PRO, text = "Sudah terbukti hasilnya"))
                db.proConDao().insert(ProCon(optionId = optKontraktorC, type = ProConType.CON, text = "Jadwal fleksibel tapi bisa molor"))
            }

            if (!existingTitles.contains("Smartphone Flagship 2024")) {
                // --- Smartphone Flagship 2024 ---
                val selPhone = db.selectionDao().insert(Selection(title = "Smartphone Flagship 2024"))
                val optIphone = db.optionDao().insert(Option(selectionId = selPhone, name = "iPhone 15 Pro"))
                val optGalaxy = db.optionDao().insert(Option(selectionId = selPhone, name = "Samsung Galaxy S24 Ultra"))
                val optPixel = db.optionDao().insert(Option(selectionId = selPhone, name = "Google Pixel 8 Pro"))

                db.priceDao().insert(Price(optionId = optIphone, amount = 22_000_000))
                db.priceDao().insert(Price(optionId = optGalaxy, amount = 20_000_000))
                db.priceDao().insert(Price(optionId = optPixel, amount = 16_000_000))

                db.proConDao().insert(ProCon(optionId = optIphone, type = ProConType.PRO, text = "Performa sangat cepat"))
                db.proConDao().insert(ProCon(optionId = optIphone, type = ProConType.CON, text = "Harga paling mahal"))
                db.proConDao().insert(ProCon(optionId = optIphone, type = ProConType.PRO, text = "Ekosistem Apple"))
                db.proConDao().insert(ProCon(optionId = optGalaxy, type = ProConType.PRO, text = "Kamera fleksibel & zoom tinggi"))
                db.proConDao().insert(ProCon(optionId = optGalaxy, type = ProConType.CON, text = "Body cukup besar"))
                db.proConDao().insert(ProCon(optionId = optGalaxy, type = ProConType.PRO, text = "Layar sangat cerah"))
                db.proConDao().insert(ProCon(optionId = optPixel, type = ProConType.PRO, text = "Kamera natural & AI canggih"))
                db.proConDao().insert(ProCon(optionId = optPixel, type = ProConType.CON, text = "Resmi belum masuk Indonesia"))
                db.proConDao().insert(ProCon(optionId = optPixel, type = ProConType.PRO, text = "Update software tercepat"))
            }
        }
    }
}