package com.tanimi.kmptestapp.service.serverAccess.api

import com.tanimi.kmptestapp.service.serverAccess.data.LineMessage
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.statement.HttpResponse

sealed class UploadImageType(
    val contentType: String
) {
    data object Jpeg: UploadImageType("image/jpeg")
    data object Png: UploadImageType("image/png")
}

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Line : Screen("line")
    data object Question : Screen("question")

    companion object {
        val startScreen = Home
    }
}

interface S3ImageApi {
    companion object {
        const val BASE_URL = "https://7gru7j1ocj.execute-api.ap-northeast-1.amazonaws.com/S3GETPUT/"
    }

    @GET("{fileName}")
    suspend fun downloadImage(@Path("fileName") fileName: String): ByteArray

    @PUT("{fileName}")
    suspend fun uploadImage(@Path("fileName") fileName: String,
                            @Body content: ByteArray,
                            @Header("Content-Type") contentType: String = "image/jpeg") {
    }
}