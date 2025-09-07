package com.raylabs.mathverse.feature.mychoice.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    nav: NavController
) {
    val state by vm.ui.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text("MyChoice Dashboard") }) }
    ) { pad ->
        Box(Modifier.padding(pad)) {
            when (state) {
                DashboardViewModel.UiState.Loading -> CircularProgressIndicator()
                is DashboardViewModel.UiState.Data -> {
                    val data = (state as DashboardViewModel.UiState.Data).selections
                    LazyColumn {
                        items(data) { sel ->
                            ElevatedCard(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { nav.navigate("detail/${sel.id}") }
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(sel.title, style = MaterialTheme.typography.titleMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}