package com.tanimi.kmptestapp.service.serverAccess

import com.tanimi.kmptestapp.service.serverAccess.api.LineApi
import com.tanimi.kmptestapp.service.serverAccess.api.QuestionApi
import com.tanimi.kmptestapp.service.serverAccess.api.createLineApi
import com.tanimi.kmptestapp.service.serverAccess.api.createQuestionApi
import com.tanimi.kmptestapp.service.serverAccess.data.LineMessage
import com.tanimi.kmptestapp.service.serverAccess.data.QuestionRequestBody
import com.tanimi.kmptestapp.service.serverAccess.data.QuestionResponseBody
import de.jensklingenberg.ktorfit.ktorfit

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class HTTPClientService {

    private val lineApi: LineApi
        get()  = ktorfit {
            baseUrl(LineApi.BASE_URL)
            httpClient(
                HttpClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
            )
        }.createLineApi()

    private val questionApi: QuestionApi
        get()  = ktorfit {
            baseUrl(QuestionApi.BASE_URL)
            httpClient(
                HttpClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                isLenient = true
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
            )
        }.createQuestionApi()

    suspend fun sendLineMessage(message: String) {
        val lineMessage = LineMessage(message)
        try {
            lineApi.sentMassage(lineMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendQuestion(question: String): String {
        val requestBody =  QuestionRequestBody(question)
        try {
            val response = questionApi.sendQuestion(requestBody)
            return response.answer
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}