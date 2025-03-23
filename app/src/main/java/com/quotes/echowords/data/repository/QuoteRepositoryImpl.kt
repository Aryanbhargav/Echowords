package com.quotes.echowords.data.repository

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.quotes.echowords.data.local.QuoteDao
import com.quotes.echowords.data.worker.FetchWorker
import com.quotes.echowords.data.worker.NotificationWorker
import com.quotes.echowords.data.worker.PeriodicWorker
import com.quotes.echowords.domain.model.Quote
import com.quotes.echowords.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class QuoteRepositoryImpl(private val workManager: WorkManager,private val quoteDao: QuoteDao):QuoteRepository {
    override fun getQuote() {
        val constraints= androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest= OneTimeWorkRequestBuilder<FetchWorker>()
            .setConstraints(constraints)
            .build()
        //workManager.enqueue(workRequest)
        val notificationWorkRequest= OneTimeWorkRequestBuilder<NotificationWorker>()
            .build()
        workManager.beginWith(workRequest)
            .then(notificationWorkRequest)
            .enqueue()
    }

    override fun getAllQuotes(): Flow<List<Quote>> =
        quoteDao.getQuotesList()


    override fun setupPeriodicWorkRequest() {
        val constraints= androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest= PeriodicWorkRequest.Builder(PeriodicWorker::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniquePeriodicWork(
            "laksdfjlasf",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}