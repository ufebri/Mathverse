package com.raylabs.mathverse.feature.mychoice.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    nav: NavController
) {
    val state by vm.ui.collectAsState()
    var query by remember { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = { Text("My Choice") },
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
        ) {
            // Search (tanpa dependency tambahan)
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                placeholder = { Text("Cari judul pemilihan…") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            when (val s = state) {
                DashboardViewModel.UiState.Loading -> LoadingState()
                is DashboardViewModel.UiState.Data -> {
                    val items = if (query.isBlank()) s.selections
                    else s.selections.filter { it.title.contains(query, ignoreCase = true) }

                    if (items.isEmpty()) {
                        EmptyState(query)
                    } else {
                        DashboardList(
                            selections = items,
                            onClick = { id -> nav.navigate("detail/$id") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyState(query: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tidak ada data", style = MaterialTheme.typography.titleMedium)
        if (query.isNotBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(
                "Pencarian: \"$query\"",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(12.dp))
        AssistChip(
            onClick = { },
            label = { Text("Buat pilihan baru") })
    }
}

@Composable
private fun DashboardList(
    selections: List<Selection>,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(selections, key = { it.id }) { sel ->
            SelectionCard(
                title = sel.title,
                onClick = { onClick(sel.id) }
            )
        }
    }
}

@Composable
private fun SelectionCard(
    title: String,
    optionsCount: Int = 0,
    updatedAt: Long? = null,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            val subtitle = buildString {
                append("$optionsCount opsi")
                if (updatedAt != null) append(" · ${formatDate(updatedAt)}")
            }
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Perbandingan harga & evaluasi",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                AssistChip(onClick = onClick, label = { Text("Buka ›") })
            }
        }
    }
}

@Composable
private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale("id"))
    return formatter.format(Date(timestamp))
}

/* ======================= PREVIEWS ======================= */

@Preview(name = "Dashboard – Light", showBackground = true, widthDp = 420)
@Composable
private fun PreviewDashboardLight() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        DashboardPreview()
    }
}

@Preview(name = "Dashboard – Dark", showBackground = true, widthDp = 420)
@Composable
private fun PreviewDashboardDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        DashboardPreview()
    }
}

@Composable
private fun DashboardPreview() {
    val demo = listOf(
        Selection(1, "Laptop Kerja 2025"),
        Selection(2, "Vendor Cloud Terbaik"),
        Selection(3, "Jasa Renovasi Rumah")
    )
    Surface { DashboardList(selections = demo, onClick = {}) }
}