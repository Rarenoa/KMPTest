package com.tanimi.kmptestapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanimi.kmptestapp.data.entity.AnswerHistory

@Dao
interface AnswerHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answerHistory: AnswerHistory)

    @Query("SELECT * FROM AnswerHistory ORDER BY created DESC")
    suspend fun getAll(): List<AnswerHistory>
}