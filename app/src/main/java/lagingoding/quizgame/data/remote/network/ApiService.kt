package lagingoding.quizgame.data.remote.network

import lagingoding.quizgame.data.remote.model.SheetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("v4/spreadsheets/{spreadsheetId}/values/{range}")
    fun getSheetData(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("key") apiKey: String
    ): Call<SheetResponse>

    @POST("v4/spreadsheets/{spreadsheetId}/values/{range}:append")
    @Headers("Content-Type: application/json")
    fun appendData(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("key") apiKey: String, // API Key for authorization
        @Body data: Map<String, Any> // Manually constructed JSON body
    ): Call<Void>
}
