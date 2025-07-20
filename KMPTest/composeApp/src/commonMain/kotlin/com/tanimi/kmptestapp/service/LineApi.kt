package com.tanimi.kmptestapp.service

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface LineApi {
    companion object {
        const val BASE_URL = "http://tmp"
    }

    @GET("line/{id}")
    suspend fun getAa(@Path("id")id: Int): String
}