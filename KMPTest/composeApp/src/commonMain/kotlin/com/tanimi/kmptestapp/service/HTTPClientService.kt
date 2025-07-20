package com.tanimi.kmptestapp.service

import de.jensklingenberg.ktorfit.ktorfit

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class HTTPClientService {

    val lineApi: LineApi
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

    fun getLineMessage() {

    }
}