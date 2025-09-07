package com.raylabs.mathverse.feature.mychoice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raylabs.mathverse.feature.mychoice.data.local.Option
import com.raylabs.mathverse.feature.mychoice.data.local.Selection
import com.raylabs.mathverse.feature.mychoice.domain.usecase.ComputeScoresUseCase
import kotlin.math.roundToInt

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
                is DetailViewModel.UiState.Loading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is DetailViewModel.UiState.Error -> ErrorState(s.msg)

                is DetailViewModel.UiState.Data -> DetailContent(ui = s)
            }
        }
    }
}

@Composable
private fun DetailContent(ui: DetailViewModel.UiState.Data) {
    val best = ui.result.rows.maxByOrNull { it.finalScore }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Judul pemilihan
        Text(
            ui.selection.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )

        // Rekomendasi akhir (jika ada)
        if (best != null) {
            BestPickCard(
                name = best.option.name,
                score = best.finalScore,
                subtitle = "Dipilih berdasarkan skor tertinggi dengan mempertimbangkan harga & pro/cons."
            )
        }

        // Header daftar opsi
        Text(
            "Perbandingan Opsi",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Daftar opsi
        ElevatedCard(Modifier.fillMaxWidth()) {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(ui.result.rows, key = { it.option.id }) { row ->
                    OptionRowCard(
                        name = row.option.name,
                        price = row.priceMin,
                        pros = row.prosCount,
                        cons = row.consCount,
                        score = row.finalScore,
                        highlighted = best?.option?.id == row.option.id
                    )
                }
            }
        }
    }
}

@Composable
private fun BestPickCard(
    name: String,
    score: Float,
    subtitle: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Rekomendasi Akhir",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                ScoreBadgePercent(score = score)
            }
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun OptionRowCard(
    name: String,
    price: Long?,
    pros: Int,
    cons: Int,
    score: Float,
    highlighted: Boolean
) {
    val container =
        if (highlighted) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val onContainer =
        if (highlighted) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface

    Surface(
        color = container,
        contentColor = onContainer,
        tonalElevation = if (highlighted) 4.dp else 0.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(8.dp))

                BoxWithConstraints {
                    val isCompact = maxWidth < 360.dp  // threshold responsif

                    if (isCompact) {
                        // COMPACT: semua pasti muat 1 baris
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LabelChip(
                                text = formatCurrencyShort(price),
                                dense = true
                            )
                            VotesChip(
                                pros = pros,
                                cons = cons,
                                dense = true
                            )
                            Spacer(Modifier.weight(1f))
                            ScoreBadgePercent(score = score, dense = true)
                        }
                    } else {
                        // WIDE: layout lengkap
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LabelChip(text = formatCurrencyShort(price))
                            ProConChip(text = "+$pros", isPro = true)
                            ProConChip(text = "-$cons", isPro = false)
                            Spacer(Modifier.weight(1f))
                            ScoreBadgePercent(score = score)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LabelChip(text: String, dense: Boolean = false) {
    val padH = if (dense) 10.dp else 12.dp
    val padV = if (dense) 6.dp else 8.dp
    val style =
        if (dense) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge

    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = padH, vertical = padV),
            style = style,
            maxLines = 1
        )
    }
}

@Composable
private fun ProConChip(text: String, isPro: Boolean) {
    val bg =
        if (isPro) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer
    val fg =
        if (isPro) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onErrorContainer
    val icon = if (isPro) Icons.Filled.ThumbUp else Icons.Filled.ThumbDown

    Row(
        Modifier
            .clip(CircleShape)
            .background(bg)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.labelLarge, color = fg, maxLines = 1)
    }
}


@Composable
private fun VotesChip(pros: Int, cons: Int, dense: Boolean = false) {
    val padH = if (dense) 10.dp else 12.dp
    val padV = if (dense) 6.dp else 8.dp
    val style =
        if (dense) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Row(
            Modifier.padding(horizontal = padH, vertical = padV),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ThumbUp, null, modifier = Modifier.size(if (dense) 16.dp else 18.dp))
            Spacer(Modifier.width(4.dp))
            Text("$pros", style = style)
            Spacer(Modifier.width(10.dp))
            Box(
                Modifier
                    .width(1.dp)
                    .height(if (dense) 12.dp else 14.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.25f))
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                Icons.Filled.ThumbDown,
                null,
                modifier = Modifier.size(if (dense) 16.dp else 18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("$cons", style = style)
        }
    }
}

@Composable
private fun ScoreBadgePercent(score: Float, dense: Boolean = false) {
    val percent = (score * 100).roundToInt()
    val bg = when {
        percent >= 70 -> MaterialTheme.colorScheme.tertiaryContainer
        percent >= 40 -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.errorContainer
    }
    val fg = when {
        percent >= 70 -> MaterialTheme.colorScheme.onTertiaryContainer
        percent >= 40 -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onErrorContainer
    }
    val padH = if (dense) 10.dp else 12.dp
    val padV = if (dense) 6.dp else 8.dp
    val style =
        if (dense) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge

    Surface(color = bg, contentColor = fg, shape = CircleShape) {
        Text(
            "$percent%",
            modifier = Modifier.padding(horizontal = padH, vertical = padV),
            style = style,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
private fun ErrorState(msg: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Terjadi kesalahan",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(6.dp))
        Text(
            msg,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun formatCurrencyShort(value: Long?): String {
    if (value == null) return "-"
    // contoh: 18_000_000 -> "Rp18 jt"
    val v = value.toDouble()
    return when {
        v >= 1_000_000_000 -> "Rp${(v / 1_000_000_000).roundToInt()} m"
        v >= 1_000_000 -> "Rp${(v / 1_000_000).roundToInt()} jt"
        v >= 1_000 -> "Rp${(v / 1_000).roundToInt()} rb"
        else -> "Rp${value}"
    }
}

/* ======================= PREVIEWS ======================= */

@Preview(name = "Detail – Light", showBackground = true, widthDp = 420)
@Composable
private fun PreviewDetailLight() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        PreviewDetailContent()
    }
}

@Preview(name = "Detail – Dark", showBackground = true, widthDp = 420)
@Composable
private fun PreviewDetailDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        PreviewDetailContent()
    }
}

@Composable
private fun PreviewDetailContent() {
    // Fake UiState.Data untuk preview tanpa perlu ViewModel/Repo
    val sel = Selection(id = 1, title = "Laptop Kerja 2025")
    val rows = listOf(
        ComputeScoresUseCase.Result.Row(
            option = Option(10, 1, "Lenovo ThinkPad"),
            priceMin = 18_000_000,
            prosCount = 3,
            consCount = 1,
            priceScore = 0.50f,
            prosScore = 0.35f,
            consScore = 0.15f,
            finalScore = 0.60f
        ),
        ComputeScoresUseCase.Result.Row(
            option = Option(11, 1, "Dell XPS"),
            priceMin = 20_000_000,
            prosCount = 2,
            consCount = 2,
            priceScore = 0.40f,
            prosScore = 0.30f,
            consScore = 0.30f,
            finalScore = 0.46f
        ),
        ComputeScoresUseCase.Result.Row(
            option = Option(12, 1, "MacBook Air"),
            priceMin = 25_000_000,
            prosCount = 1,
            consCount = 3,
            priceScore = 0.20f,
            prosScore = 0.30f,
            consScore = 0.50f,
            finalScore = 0.10f
        )
    )
    val result = ComputeScoresUseCase.Result(bestOptionId = 1, rows = rows)
    DetailContent(ui = DetailViewModel.UiState.Data(selection = sel, result = result))
}