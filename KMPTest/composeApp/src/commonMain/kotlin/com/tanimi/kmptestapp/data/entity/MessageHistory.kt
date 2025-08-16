package com.tanimi.kmptestapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@Entity
data class MessageHistory @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: String,
    val message: String,
    val created: String
)