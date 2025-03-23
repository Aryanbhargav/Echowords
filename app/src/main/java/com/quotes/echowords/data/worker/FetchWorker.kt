package com.quotes.echowords.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.quotes.echowords.data.local.QuoteDao
import com.quotes.echowords.data.mapper.toDomain
import com.quotes.echowords.data.remote.ApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


 const val ONE_TIME_WORK_REQUEST="one time work request"
@HiltWorker
class FetchWorker @AssistedInject constructor(
    @Assisted private val context:Context,
    @Assisted workerParameters:WorkerParameters,
    private val dao: QuoteDao,
    private val apiService: ApiService
):CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
       return try {
            val response=apiService.getQuote().toDomain(ONE_TIME_WORK_REQUEST)
            dao.insert(response)
           val data= Data.Builder()
               .putString(QUOTE,Gson().toJson(response))
               .build()
           Result.success(data)
       }catch (e:Exception){
           println("exception: ${e.cause}")
           Result.failure()
       }
    }

}