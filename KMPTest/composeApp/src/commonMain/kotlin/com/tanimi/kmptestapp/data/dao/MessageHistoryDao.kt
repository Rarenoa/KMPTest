package com.tanimi.kmptestapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.tanimi.kmptestapp.data.entity.MessageHistory

@Dao
interface MessageHistoryDao {
    @Query("SELECT * FROM messagehistory")
    fun getAll(): List<MessageHistory>
}