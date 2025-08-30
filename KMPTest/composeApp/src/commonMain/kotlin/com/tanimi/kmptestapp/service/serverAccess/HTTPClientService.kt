package com.tanimi.kmptestapp.service.serverAccess

import com.tanimi.kmptestapp.readFileAsByteArray
import com.tanimi.kmptestapp.service.serverAccess.api.LineApi
import com.tanimi.kmptestapp.service.serverAccess.api.QuestionApi
import com.tanimi.kmptestapp.service.serverAccess.api.S3ImageApi
import com.tanimi.kmptestapp.service.serverAccess.api.UploadImageType
import com.tanimi.kmptestapp.service.serverAccess.api.createLineApi
import com.tanimi.kmptestapp.service.serverAccess.api.createQuestionApi
import com.tanimi.kmptestapp.service.serverAccess.api.createS3ImageApi
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

    private val s3ImageApi: S3ImageApi
        get()  = ktorfit {
            baseUrl(S3ImageApi.BASE_URL)
            httpClient(
                HttpClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
            )
        }.createS3ImageApi()

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

    suspend fun uploadImage(fileName: String, type: UploadImageType) {
        val bytes = readFileAsByteArray("")
        try {
            s3ImageApi.uploadImage(
                fileName = fileName,
                content = bytes,
                contentType = type.contentType
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun downloadImage(fileName: String): ByteArray? {
        try {
            return s3ImageApi.downloadImage(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}