package com.tanimi.kmptestapp.service.data

import kotlinx.serialization.Serializable

@Serializable
data class LineMessage(
    val message: String
) {
}