package lagingoding.quizgame.ui.sbar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import lagingoding.quizgame.BuildConfig
import lagingoding.quizgame.R
import lagingoding.quizgame.data.remote.model.SheetResponse
import lagingoding.quizgame.data.remote.network.ApiClient

class BarcodeScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        val scanButton: Button = findViewById(R.id.scanButton)
        val validateButton: Button = findViewById(R.id.validateButton)
        val barcodeInput: EditText = findViewById(R.id.barcodeInput)

        // Mulai scanning barcode saat tombol scan ditekan
        scanButton.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

        // Validasi barcode yang dimasukkan secara manual
        validateButton.setOnClickListener {
            val barcode = barcodeInput.text.toString()
            if (barcode.isNotEmpty()) {
                validateBarcode(barcode)
            } else {
                Toast.makeText(this, "Masukkan barcode terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val barcodeResultText: TextView = findViewById(R.id.barcodeResultText)
        if (result != null) {
            if (result.contents == null) {
                barcodeResultText.text = "Scanning canceled"
            } else {
                // Tampilkan hasil scan dan validasi barcode yang di-scan
                barcodeResultText.text = "Scanned barcode: ${result.contents}"
                validateBarcode(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun validateBarcode(barcode: String) {
        val spreadsheetId = BuildConfig.SPREADSHEET_ID
        val range = "Sheet1!A1:B2" // Atur sesuai dengan range data di spreadsheet Anda
        val apiKey = BuildConfig.API_KEY_GSHEET // Masukkan API key Google Sheets API Anda
        val barcodeResultText: TextView = findViewById(R.id.barcodeResultText)

        ApiClient.service.getSheetData(spreadsheetId, range, apiKey)
            .enqueue(object : Callback<SheetResponse> {
                override fun onResponse(call: Call<SheetResponse>, response: Response<SheetResponse>) {
                    try {
                        if (response.isSuccessful) {
                            val sheetData = response.body()?.values
                            if (sheetData != null) {
                                val result = sheetData.find { it[0] == barcode }
                                if (result != null) {
                                    barcodeResultText.text = "Barcode valid: ${result[1]}"
                                } else {
                                    barcodeResultText.text = "Barcode tidak ditemukan!"
                                }
                            }
                        } else {
                            Toast.makeText(this@BarcodeScannerActivity, "Request gagal", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@BarcodeScannerActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SheetResponse>, t: Throwable) {
                    Toast.makeText(this@BarcodeScannerActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
