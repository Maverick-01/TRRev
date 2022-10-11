package com.maverick.trrev

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Content-Type: application/json",
        "X-Requested-With: X",
        "Accept: application/json"
    )
    @POST("http://199.192.26.248:8000/sap/opu/odata/sap/ZCDS_TEST_REGISTER_CDS/ZCDS_TEST_REGISTER/")
    suspend fun createUser(@Body requestBody: RequestBody): Response<ResponseBody>
}