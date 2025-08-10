package com.tanimi.kmptestapp

import androidx.room.RoomDatabase
import com.tanimi.kmptestapp.data.AppDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect object AppContext

expect fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>