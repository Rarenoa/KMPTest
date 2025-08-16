package com.tanimi.kmptestapp.service.serverAccess.data

import kotlinx.serialization.Serializable

@Serializable
data class LineMessage(
    val message: String
) {
}