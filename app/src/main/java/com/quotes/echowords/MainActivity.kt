package com.quotes.echowords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quotes.echowords.presentation.QuoteViewModel
import com.quotes.echowords.ui.theme.EchoWordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EchoWordsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onSurface
                ) {
                    val viewModel = hiltViewModel<QuoteViewModel>()
                    MainScreen(viewModel) {
                        viewModel.getQuote()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: QuoteViewModel, fetchQuote: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "EchoWords", style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(Color.Black,  Offset(4f, 4f) , blurRadius = 2f)

                    )
                )
            }, actions = {
                IconButton(onClick = fetchQuote) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            },
                modifier = Modifier.background(Color.Transparent),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFAA66F8))
            )

        },
        modifier = Modifier.fillMaxSize()
    ) {

        if (uiState.data.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Nothing found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFAA66F8), // Dark purple
                                Color(0xFF13ADF5)         // Black
                            )
                        )
                    )
            ) {
                items(uiState.data) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFF1EEED), // Dark purple
                                        Color(0xFFFCF1F1)          // Black
                                    )
                                ), RoundedCornerShape(16.dp)
                            )
                            .fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "“ ${it.quote} ”", style = TextStyle(
                                    fontSize = 17.sp,
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Author: ${it.author}", style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF03A9F4),

                                    )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = viewModel.formatTimestampToDMY(it.time).toString(),
                                    style = TextStyle(
                                        fontSize = 14.sp,  // Set the font size
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF656565),

                                        )
                                )
                                Text(
                                    text = it.workType, style = TextStyle(
                                        fontSize = 14.sp,  // Set the font size,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB65AC5),

                                        )
                                )
                            }
                        }
                    }
                }
            }
        }

    }


}