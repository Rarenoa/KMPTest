package com.tanimi.kmptestapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageHistory(
    @PrimaryKey
    val id: String,
    val message: String,
    val created: String
)