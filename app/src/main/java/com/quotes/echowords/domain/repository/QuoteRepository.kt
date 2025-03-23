package com.quotes.echowords.domain.repository

import androidx.compose.ui.unit.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import com.quotes.echowords.data.worker.FetchWorker
import com.quotes.echowords.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository{

        fun getQuote()

        fun getAllQuotes(): Flow<List<Quote>>

        fun setupPeriodicWorkRequest()

}
