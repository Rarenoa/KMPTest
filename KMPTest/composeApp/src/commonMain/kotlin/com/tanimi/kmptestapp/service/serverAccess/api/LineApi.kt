package com.tanimi.kmptestapp.service.serverAccess.api

import com.tanimi.kmptestapp.service.serverAccess.data.LineMessage
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST

interface LineApi {
    companion object {
        const val BASE_URL = "https://line-onlinenote.ddns.net/"
    }

    @POST("send_message")
    @Headers("Content-Type: application/json")
    suspend fun sentMassage(
        @Body message: LineMessage
    )
}