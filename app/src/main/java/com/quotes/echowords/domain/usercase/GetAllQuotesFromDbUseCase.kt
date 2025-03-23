package com.quotes.echowords.domain.usercase

import com.quotes.echowords.domain.repository.QuoteRepository
import javax.inject.Inject

class GetAllQuotesFromDbUseCase @Inject constructor(private val quoteRepository: QuoteRepository) {
    operator fun invoke()= quoteRepository.getAllQuotes()
}