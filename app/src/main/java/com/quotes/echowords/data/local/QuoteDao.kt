package com.quotes.echowords.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quotes.echowords.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(quote: Quote)

        @Query("SELECT * FROM Quote ORDER BY time DESC")
        fun getQuotesList(): Flow<List<Quote>>

}