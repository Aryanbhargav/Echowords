package com.quotes.echowords.data.remote

import com.quotes.echowords.data.model.QuoteDTO
import retrofit2.http.GET

interface ApiService {

    // https://dummyjson.com/quotes/random

    @GET("quotes/random")
    suspend fun getQuote(): QuoteDTO
}