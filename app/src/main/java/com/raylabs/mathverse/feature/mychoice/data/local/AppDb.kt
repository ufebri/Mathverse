package com.raylabs.mathverse.feature.mychoice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [Selection::class, Option::class, ProCon::class, Price::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun selectionDao(): SelectionDao
    abstract fun optionDao(): OptionDao
    abstract fun proConDao(): ProConDao
    abstract fun priceDao(): PriceDao
}

class Converters {
    @TypeConverter
    fun fromType(type: ProConType): String = type.name

    @TypeConverter
    fun toType(value: String): ProConType = ProConType.valueOf(value)
}