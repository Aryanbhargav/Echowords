package com.quotes.echowords.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.quotes.echowords.data.local.QuoteDao
import com.quotes.echowords.data.local.QuoteDatabase
import com.quotes.echowords.data.remote.ApiService
import com.quotes.echowords.data.repository.QuoteRepositoryImpl
import com.quotes.echowords.domain.repository.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAPiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteDatabase(app: Application):QuoteDatabase{
        return Room.databaseBuilder(
            app,
            QuoteDatabase::class.java,
            "quotedb.db"
        )
            .build()
    }

    @Provides
    fun provideQuoteDao(quoteDatabase: QuoteDatabase): QuoteDao {
        return quoteDatabase.getQuoteDao()
    }

    @Provides
    @Singleton
    fun provideWorkmanager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideQuoteRepository(workManager: WorkManager, quoteDao: QuoteDao): QuoteRepository {
        return QuoteRepositoryImpl(workManager, quoteDao)
    }
}
