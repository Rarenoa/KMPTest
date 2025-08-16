package com.tanimi.kmptestapp

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