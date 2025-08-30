package com.tanimi.kmptestapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi

@Serializable
@Entity
data class AnswerHistory @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: String,
    val question: String,
    val answer: String,
    val created: String
)