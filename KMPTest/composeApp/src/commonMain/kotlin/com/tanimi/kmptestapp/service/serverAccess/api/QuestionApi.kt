package com.tanimi.kmptestapp.service.serverAccess.api

import com.tanimi.kmptestapp.service.serverAccess.data.QuestionRequestBody
import com.tanimi.kmptestapp.service.serverAccess.data.QuestionResponseBody
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST

interface QuestionApi {
    companion object {
        const val BASE_URL = "https://er6wk3nv5g.execute-api.ap-northeast-1.amazonaws.com/Stage/"
    }

    @POST("ask")
    @Headers("Content-Type: application/json")
    suspend fun sendQuestion (
        @Body question: QuestionRequestBody
    ) : QuestionResponseBody
}