package com.tanimi.kmptestapp

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.RoomDatabase
import com.tanimi.kmptestapp.data.AppDatabase
import com.tanimi.kmptestapp.service.ocr.OCRService

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect object AppContext
expect fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

expect fun getUuid(): String
expect fun dateTimeStringNow(): String

expect class OCRServiceImpl(): OCRService

expect fun readFileAsByteArray(path: String): ByteArray
expect fun saveFile(path: String, bytes: ByteArray)
expect fun ByteArray.toImageBitmap(): ImageBitmap