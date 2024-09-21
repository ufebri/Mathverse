package lagingoding.quizgame.ui.snfc

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import lagingoding.quizgame.BuildConfig
import lagingoding.quizgame.data.remote.model.SheetResponse
import lagingoding.quizgame.data.remote.network.ApiClient
import lagingoding.quizgame.databinding.ActivityNfcKtpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NfcKtpActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var binding: ActivityNfcKtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup View Binding
        binding = ActivityNfcKtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "Perangkat ini tidak mendukung NFC", Toast.LENGTH_LONG).show()
            finish() // Keluar dari activity jika NFC tidak didukung
        } else if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "NFC tidak diaktifkan, silakan aktifkan NFC", Toast.LENGTH_LONG).show()
            startActivity(Intent(android.provider.Settings.ACTION_NFC_SETTINGS))
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)  // NFC ReaderCallback requires at least Android 4.4 (KitKat)
    override fun onResume() {
        super.onResume()

        // Aktifkan mode pembacaan NFC
        val options = Bundle()
        nfcAdapter?.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, options)
    }

    override fun onPause() {
        super.onPause()

        // Nonaktifkan mode pembacaan NFC saat aplikasi dihentikan
        nfcAdapter?.disableReaderMode(this)
    }

    // Implementasi callback saat tag NFC terdeteksi
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onTagDiscovered(tag: Tag?) {
        tag?.let {
            val isoDep = IsoDep.get(it)
            isoDep?.let { iso ->
                iso.connect()

                // Kirim APDU command untuk mendapatkan data NIK dari KTP
                val commandApdu: ByteArray = getCommandApdu()
                val result: ByteArray = iso.transceive(commandApdu)

                // Proses hasilnya untuk mendapatkan NIK
                val nik = processNIK(result)

                // Tampilkan NIK di UI menggunakan binding (Karena UI hanya bisa diubah di thread utama, gunakan runOnUiThread)
                runOnUiThread {
                    binding.nfcPrompt.text = "NIK: $nik"
                }

                // Validasi dan simpan NIK ke Google Sheets
                validateAndSaveNIK(nik)
            }
        }
    }

    private fun getCommandApdu(): ByteArray {
        // APDU command untuk mengambil data dari KTP
        return byteArrayOf(0x00, 0xA4.toByte(), 0x04, 0x00, 0x07)
    }

    private fun processNIK(result: ByteArray): String {
        // Contoh memproses byte array dari NFC (mengubah menjadi string yang dapat dibaca)

        // Misalnya, jika NIK dimulai dari indeks ke-10 dan panjangnya 16 byte
        // Ini perlu disesuaikan dengan format data yang sebenarnya
        val startIndex = 10  // Indeks awal dari data NIK di byte array
        val endIndex = startIndex + 16  // Panjang NIK adalah 16 karakter

        // Pastikan byte array memiliki cukup panjang
        if (result.size >= endIndex) {
            // Mengambil NIK dari byte array dan mengonversinya menjadi string
            val nikBytes = result.copyOfRange(startIndex, endIndex)
            return nikBytes.toString(Charsets.UTF_8)  // Mengonversi byte array menjadi string
        }

        // Jika tidak ditemukan, kembalikan string kosong atau pesan error
        return "NIK tidak ditemukan"
    }


    private fun validateAndSaveNIK(nik: String) {
        ApiClient.service.getSheetData(BuildConfig.SPREADSHEET_ID, "Sheet1!A:A", BuildConfig.API_KEY_GSHEET)
            .enqueue(object : Callback<SheetResponse> {
                override fun onResponse(call: Call<SheetResponse>, response: Response<SheetResponse>) {
                    if (response.isSuccessful) {
                        val existingData = response.body()?.values
                        val alreadyExists = existingData?.any { it.contains(nik) } ?: false

                        if (alreadyExists) {
                            runOnUiThread {
                                Toast.makeText(this@NfcKtpActivity, "NIK sudah ada, tidak bisa duplikasi!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            saveNIKToSheets(nik)
                        }
                    }
                }

                override fun onFailure(call: Call<SheetResponse>, t: Throwable) {
                    runOnUiThread {
                        Toast.makeText(this@NfcKtpActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun saveNIKToSheets(nik: String) {
        // Create a Map for the JSON body
        val data = mapOf(
            "range" to "Sheet1!A:A", // The range where the data should be inserted
            "majorDimension" to "ROWS", // We are inserting data row-wise
            "values" to listOf(listOf(nik)) // The actual data to be inserted (list of rows)
        )

        ApiClient.service.appendData(BuildConfig.SPREADSHEET_ID, "Sheet1!A:A", BuildConfig.API_KEY_GSHEET, data)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(this@NfcKtpActivity, "NIK berhasil disimpan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    runOnUiThread {
                        Toast.makeText(this@NfcKtpActivity, "Gagal menyimpan NIK", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}
