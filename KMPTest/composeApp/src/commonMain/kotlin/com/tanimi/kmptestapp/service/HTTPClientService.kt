package com.tanimi.kmptestapp.service

import com.tanimi.kmptestapp.service.data.LineMessage
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

    suspend fun sendLineMessage(message: String) {
        println("sendMessage")
        val lineMessage = LineMessage(message)
        try {
            lineApi.sentMassage(lineMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}