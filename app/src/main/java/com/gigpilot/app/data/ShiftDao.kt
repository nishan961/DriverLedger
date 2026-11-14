package com.gigpilot.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {

    @Query("SELECT * FROM shifts WHERE endMs = 0 LIMIT 1")
    fun observeActive(): Flow<Shift?>

    @Query("SELECT * FROM shifts WHERE startMs >= :dayStart AND startMs < :dayEnd ORDER BY startMs")
    fun observeForDay(dayStart: Long, dayEnd: Long): Flow<List<Shift>>

    @Query("SELECT * FROM shifts WHERE id = :id")
    suspend fun getById(id: Long): Shift?

    @Insert
    suspend fun insert(shift: Shift): Long

    @Update
    suspend fun update(shift: Shift)
}
