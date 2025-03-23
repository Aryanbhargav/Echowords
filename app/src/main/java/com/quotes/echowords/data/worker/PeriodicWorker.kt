package com.quotes.echowords.data.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.quotes.echowords.CHANNEL
import com.quotes.echowords.R
import com.quotes.echowords.data.local.QuoteDao
import com.quotes.echowords.data.mapper.toDomain
import com.quotes.echowords.data.remote.ApiService
import com.quotes.echowords.domain.model.Quote
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val PERIODIC_WORK_REQUEST="periodic work request"

@HiltWorker
class PeriodicWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dao: QuoteDao,
    private val apiService: ApiService
): CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val response=apiService.getQuote().toDomain(PERIODIC_WORK_REQUEST)
            dao.insert(response)
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val notification = NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Quote's")
                        .setContentText(response.quote.plus(" ${response.author}"))
                        .build()
                    NotificationManagerCompat.from(context)
                        .notify(1, notification)
                }
            }
            Result.success()
        }catch (e:Exception){
            println("exception: ${e.cause}")
            Result.failure()
        }
    }

}