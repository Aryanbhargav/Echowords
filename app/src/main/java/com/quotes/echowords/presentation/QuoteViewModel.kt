package com.quotes.echowords.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.quotes.echowords.domain.model.Quote
import com.quotes.echowords.domain.usercase.GetAllQuotesFromDbUseCase
import com.quotes.echowords.domain.usercase.GetQuoteUseCase
import com.quotes.echowords.domain.usercase.SetPeriodicWorkRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val setPeriodicWorkRequestUseCase: SetPeriodicWorkRequestUseCase,
    private val getAllQuotesFromDbUseCase: GetAllQuotesFromDbUseCase
) : ViewModel() {

    val uiState=getAllQuotesFromDbUseCase.invoke()
        .map { UiState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(emptyList()))
    init {
        setPeriodicWorkRequestUseCase.invoke()
    }
   fun getQuote(){
       getQuoteUseCase.invoke()
   }
    fun formatTimestampToDMY(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }


}
data class UiState(val data:List<Quote>)