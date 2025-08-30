package com.tanimi.kmptestapp.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.tanimi.kmptestapp.data.dao.AnswerHistoryDao
import com.tanimi.kmptestapp.data.dao.MessageHistoryDao
import com.tanimi.kmptestapp.data.entity.AnswerHistory
import com.tanimi.kmptestapp.data.entity.MessageHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Database(entities = [MessageHistory::class, AnswerHistory::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getMessageHistoryDao(): MessageHistoryDao
    abstract fun getAnswerHistoryDao(): AnswerHistoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor: RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder
        .addMigrations()
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}