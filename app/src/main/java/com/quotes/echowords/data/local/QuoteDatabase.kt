package com.quotes.echowords.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quotes.echowords.domain.model.Quote

@Database(entities = [Quote::class],
    version = 1)
abstract class QuoteDatabase :RoomDatabase(){
    /*abstract val dao:QuoteDao*/
    abstract fun getQuoteDao(): QuoteDao
}