package com.raylabs.mathverse.feature.mychoice.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    vm: DetailViewModel,
    onBack: () -> Unit
) {
    val state by vm.ui.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                }
            )
        }
    ) { pad ->
        Box(Modifier.padding(pad)) {
            when (val s = state) {
                is DetailViewModel.UiState.Loading -> CircularProgressIndicator()
                is DetailViewModel.UiState.Error -> Text("Error: ${s.msg}")
                is DetailViewModel.UiState.Data -> {
                    Column(Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        Text(s.selection.title, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(12.dp))
                        LazyColumn {
                            items(s.result.rows) { row ->
                                Row(Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)) {
                                    Text(row.option.name, Modifier.weight(1f))
                                    Text(formatCurrency(row.priceMin))
                                    Spacer(Modifier.width(8.dp))
                                    Text("Pro:${row.prosCount}")
                                    Spacer(Modifier.width(8.dp))
                                    Text("Con:${row.consCount}")
                                    Spacer(Modifier.width(8.dp))
                                    Text(String.format("%.2f", row.finalScore))
                                }
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                        s.result.rows.firstOrNull()?.let { best ->
                            ElevatedCard {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        "Rekomendasi Akhir",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        "${best.option.name} (Skor ${
                                            String.format(
                                                "%.2f",
                                                best.finalScore
                                            )
                                        })"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun formatCurrency(value: Long?): String {
    if (value == null) return "-"
    val nf = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    nf.maximumFractionDigits = 0
    return nf.format(value)
}