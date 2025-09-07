package com.raylabs.mathverse.data.remote.network

import com.raylabs.mathverse.data.remote.model.SheetResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("v4/spreadsheets/{spreadsheetId}/values/{range}")
    fun getSheetData(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("key") apiKey: String
    ): Call<SheetResponse>
}
