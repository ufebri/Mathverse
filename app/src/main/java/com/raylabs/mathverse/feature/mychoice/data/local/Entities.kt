package com.raylabs.mathverse.feature.mychoice.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selection")
data class Selection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val weightPrice: Float = 0.5f,
    val weightPros: Float = 0.3f,
    val weightCons: Float = 0.2f
)

@Entity(tableName = "option")
data class Option(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val selectionId: Long,
    val name: String
)

enum class ProConType { PRO, CON }

@Entity(tableName = "procon")
data class ProCon(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val optionId: Long,
    val type: ProConType,
    val text: String
)

@Entity(tableName = "price")
data class Price(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val optionId: Long,
    val amount: Long
)