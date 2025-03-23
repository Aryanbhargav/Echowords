package com.quotes.echowords.data.mapper

import com.quotes.echowords.data.model.QuoteDTO
import com.quotes.echowords.domain.model.Quote

fun QuoteDTO.toDomain(workType: String): Quote {
    return Quote(author, id, quote, workType)
}