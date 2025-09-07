package com.raylabs.mathverse.feature.mychoice.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SelectionDao {
    @Query("SELECT * FROM selection ORDER BY id DESC")
    fun observeSelections(): Flow<List<Selection>>

    @Insert suspend fun insert(selection: Selection): Long
    @Update suspend fun update(selection: Selection)
    @Delete suspend fun delete(selection: Selection)
}

@Dao
interface OptionDao {
    @Query("SELECT * FROM option WHERE selectionId = :id")
    suspend fun getOptions(id: Long): List<Option>
    @Insert suspend fun insert(option: Option): Long
}

@Dao
interface ProConDao {
    @Query("SELECT * FROM procon WHERE optionId IN (:ids)")
    suspend fun getByOptionIds(ids: List<Long>): List<ProCon>
    @Insert suspend fun insert(pc: ProCon)
}

@Dao
interface PriceDao {
    @Query("SELECT * FROM price WHERE optionId IN (:ids)")
    suspend fun getByOptionIds(ids: List<Long>): List<Price>
    @Insert suspend fun insert(price: Price)
}