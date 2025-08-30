package com.tanimi.kmptestapp.service.serverAccess.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestBody(
    val question: String
)

@Serializable
data class QuestionResponseBody(
    val question: String,
    val answer: String
)

