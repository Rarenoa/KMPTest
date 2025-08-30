package com.tanimi.kmptestapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanimi.kmptestapp.data.entity.MessageHistory

@Dao
interface MessageHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(messageHistory: MessageHistory)

    @Query("SELECT * FROM messagehistory")
    suspend fun getAll(): List<MessageHistory>
}